package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.BlackList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018-08-09
 */
@Repository
public interface BlackListDAO extends MongoRepository<BlackList, String> {

    boolean existsByUserIdAndBlackedUserId(String userId, String anotherUserId);

    boolean deleteByIdAndUserId(String id, String userId);
}
