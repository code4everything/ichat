package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.MailSender;
import com.zhazhapan.util.ThreadPool;
import com.zhazhapan.util.enums.LogLevel;
import org.apache.log4j.Logger;
import org.code4everything.ichat.constant.ConfigConsts;
import org.code4everything.ichat.dao.LoggerDAO;
import org.code4everything.ichat.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018-08-01
 */
@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger logger = Logger.getLogger(CommonServiceImpl.class);

    private static final String CLASS_NAME = CommonServiceImpl.class.getName();

    private final LoggerDAO loggerDAO;

    private final RedisTemplate<String, String> redis;

    @Autowired
    public CommonServiceImpl(LoggerDAO loggerDAO, RedisTemplate<String, String> redis) {
        this.loggerDAO = loggerDAO;
        this.redis = redis;
    }

    @Override
    public void sendCode(String email, String code) {
        ThreadPool.executor.execute(() -> {
            String title = Checker.checkNull(redis.opsForValue().get(ConfigConsts.EMAIL_TEMPLATE_CODE_TITLE), "验证码");
            String content = Checker.checkNull(redis.opsForValue().get(ConfigConsts.EMAIL_TEMPLATE_CODE_CONTENT), "{}");
            content = StrUtil.format(content, code);
            try {
                MailSender.sendMail(email, title, content);
            } catch (Exception e) {
                logger.error(e.getMessage());
                String msg = StrUtil.format("send verify code[{}] to email[{}] failed: " + e.getMessage(), code, email);
                saveLog(LogLevel.ERROR, CLASS_NAME + "#sendCode", msg, "");
            }
        });
    }

    @Override
    public void saveLog(LogLevel level, String method, String description, String userId) {
        org.code4everything.ichat.domain.Logger log = new org.code4everything.ichat.domain.Logger();
        log.setDescription(description);
        log.setLevel(level.toString());
        log.setMethod(method);
        log.setUserId(userId);
        log.setId(RandomUtil.simpleUUID());
        log.setTime(System.currentTimeMillis());
        loggerDAO.insert(log);
    }

    @Override
    public void saveCode(String key, String code) {
        redis.opsForValue().set(key, code, ValueConsts.THREE_INT, TimeUnit.MINUTES);
    }
}
