package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.PrivateMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/8/29
 */
@Repository
public interface PrivateMessageDAO extends MongoRepository<PrivateMessage, String> {

}
