package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.MailSender;
import com.zhazhapan.util.RandomUtils;
import com.zhazhapan.util.ThreadPool;
import org.apache.log4j.Logger;
import org.code4everything.ichat.dao.LoggerDAO;
import org.code4everything.ichat.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018-08-01
 */
@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger logger = Logger.getLogger(CommonServiceImpl.class);

    private static final String CLASS_NAME = CommonServiceImpl.class.getName();

    private final LoggerDAO loggerDAO;

    @Autowired
    public CommonServiceImpl(LoggerDAO loggerDAO) {this.loggerDAO = loggerDAO;}

    @Override
    public void sendCode(String email, int method) {
        ThreadPool.executor.execute(() -> {
            try {
                MailSender.sendMail(email, "验证码", String.valueOf(method + RandomUtils.getRandomInteger(6)));
            } catch (Exception e) {
                logger.error(e.getMessage());
                saveLog(ValueConsts.ERROR_EN, CLASS_NAME + "#sendCode", email + " 发送验证码失败", "");
            }
        });
    }

    @Override
    public void saveLog(String level, String method, String description, String userId) {
        org.code4everything.ichat.domain.Logger log = new org.code4everything.ichat.domain.Logger();
        log.setDescription(description);
        log.setLevel(level.toUpperCase());
        log.setMethod(method);
        log.setUserId(userId);
        log.setId(RandomUtil.simpleUUID());
        log.setTime(System.currentTimeMillis());
        loggerDAO.insert(log);
    }
}
