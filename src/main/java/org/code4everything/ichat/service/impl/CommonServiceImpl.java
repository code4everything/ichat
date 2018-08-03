package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.MailSender;
import com.zhazhapan.util.ThreadPool;
import com.zhazhapan.util.enums.LogLevel;
import org.apache.log4j.Logger;
import org.code4everything.ichat.constant.ConfigConsts;
import org.code4everything.ichat.constant.DefaultConfigValueConsts;
import org.code4everything.ichat.dao.DocumentDAO;
import org.code4everything.ichat.dao.LoggerDAO;
import org.code4everything.ichat.domain.Document;
import org.code4everything.ichat.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    private final DocumentDAO documentDAO;

    @Autowired
    public CommonServiceImpl(LoggerDAO loggerDAO, RedisTemplate<String, String> redis, DocumentDAO documentDAO) {
        this.loggerDAO = loggerDAO;
        this.redis = redis;
        this.documentDAO = documentDAO;
    }

    @Override
    public String saveDocument(String local, String url, MultipartFile avatar, String userId) {
        Document document = documentDAO.findDocumentByLocal(local);
        // 已经存在该文件
        if (Checker.isNotNull(document)) {
            return document.getUrl();
        }
        document = documentDAO.findDocumentByUrl(url);
        // 映射路径已经存在
        if (Checker.isNotNull(document)) {
            return url;
        }
        if (saveFile(local, avatar, userId)) {
            documentDAO.save(new Document(RandomUtil.simpleUUID(), local, url, System.currentTimeMillis()));
            return url;
        }
        return "内部错误：写入头像文件失败";
    }

    @Override
    public boolean saveFile(String filename, MultipartFile file, String userId) {
        try {
            FileExecutor.writeByteArrayToFile(new File(filename), file.getBytes());
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage());
            String msg = "write avatar to disk error: " + e.getMessage();
            saveLog(LogLevel.ERROR, CLASS_NAME + "#saveFile", msg, userId);
            return false;
        }
    }

    @Override
    public void sendCode(String email, String code) {
        ThreadPool.executor.execute(() -> {
            String title = Checker.checkNull(redis.opsForValue().get(ConfigConsts.EMAIL_TEMPLATE_CODE_TITLE),
                    DefaultConfigValueConsts.EMAIL_TEMPLATE_CODE_TITLE);
            String content = Checker.checkNull(redis.opsForValue().get(ConfigConsts.EMAIL_TEMPLATE_CODE_CONTENT),
                    DefaultConfigValueConsts.EMAIL_TEMPLATE_CODE_CONTENT);
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

    @Override
    public String getString(String key) {
        return redis.opsForValue().get(key);
    }
}
