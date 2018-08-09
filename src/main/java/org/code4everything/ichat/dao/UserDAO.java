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

    User findByUid(String uid);

    boolean existsByIdAndPassword(String id, String password);

    Integer countByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
