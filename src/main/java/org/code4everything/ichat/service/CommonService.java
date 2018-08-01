package org.code4everything.ichat.service;

/**
 * @author pantao
 * @since 2018-08-01
 */
public interface CommonService {

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @param method 方式方式
     *
     * @return 是否发送成功
     */
    void sendCode(String email, int method);

    void saveLog(String level, String method, String description, String userId);
}
