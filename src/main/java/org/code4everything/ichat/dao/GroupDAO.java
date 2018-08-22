package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/8/13
 */
@Repository
public interface GroupDAO extends MongoRepository<Group, String> {

    boolean deleteByIdAndCreator(String id, String creator);
}
