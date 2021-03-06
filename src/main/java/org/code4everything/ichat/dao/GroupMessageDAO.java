package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.GroupMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/8/28
 */
@Repository
public interface GroupMessageDAO extends MongoRepository<GroupMessage, String> {

}
