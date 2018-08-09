package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2018-08-07
 */
@Repository
public interface ContactDAO extends MongoRepository<Contact, String> {

    List<Contact> findByUserIdAndStatus(String userId, String status);

    boolean deleteByUserIdAndFriendId(String userId, String friendId);

    boolean removeByIdAndUserId(String id, String userId);

    Contact findByUserIdAndFriendId(String userId, String friendId);

    Contact findByUserIdAndFriendIdAndStatus(String userId, String friendId, String status);

    boolean existsByUserIdAndFriendIdAndStatus(String userId, String friendId, String status);
}
