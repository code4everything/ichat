package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018-08-01
 */
@Repository
public interface UserDAO extends MongoRepository<User, String> {

    /**
     * 统计邮箱注册的个数
     *
     * @param email 邮箱
     *
     * @return 个数
     */
    Integer countByEmail(String email);

    /**
     * 通过邮箱和密码查找用户
     *
     * @param email 邮箱
     * @param password 密码
     *
     * @return {@link User}
     */
    User findByEmailAndPassword(String email, String password);
}
