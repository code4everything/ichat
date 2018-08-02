package org.code4everything.ichat.service;

import com.zhazhapan.util.enums.LogLevel;

/**
 * @author pantao
 * @since 2018-08-01
 */
public interface CommonService {

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @param code 方式方式
     */
    void sendCode(String email, String code);

    void saveLog(LogLevel level, String method, String description, String userId);

    void saveCode(String key, String code);

    String getByRedisWithoutMongo(String key);
}
