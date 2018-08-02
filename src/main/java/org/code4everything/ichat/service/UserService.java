package org.code4everything.ichat.service;

import org.code4everything.ichat.domain.User;

/**
 * @author pantao
 * @since 2018-08-01
 */
public interface UserService {

    /**
     * 验证邮箱是否存在
     *
     * @param email 有限
     *
     * @return 是否存在
     */
    boolean emailExists(String email);

    void saveUser(User user);

    User login(String email, String password);

    void resetPassword(String email, String password);
}
