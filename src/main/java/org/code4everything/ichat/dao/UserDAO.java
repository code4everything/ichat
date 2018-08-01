package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author pantao
 * @since 2018-08-01
 */
public interface UserDAO extends MongoRepository<User, String> {

    /**
     * 统计邮箱注册的个数
     *
     * @param email 邮箱
     *
     * @return 个数
     */
    Integer countByEmail(String email);
}
