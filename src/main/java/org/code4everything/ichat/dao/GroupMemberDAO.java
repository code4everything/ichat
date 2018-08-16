package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.GroupMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/8/16
 */
@Repository
public interface GroupMemberDAO extends MongoRepository<GroupMember, String> {

}
