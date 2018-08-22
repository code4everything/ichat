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

    User getUserByPhone(String phone);

    User getUserByEmail(String email);

    User findByUid(String uid);

    User getUserById(String id);

    boolean existsByIdAndPassword(String id, String password);

    Integer countByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
