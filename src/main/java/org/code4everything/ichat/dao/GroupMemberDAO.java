package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.GroupMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2018/8/16
 */
@Repository
public interface GroupMemberDAO extends MongoRepository<GroupMember, String> {

    boolean deleteByGroupId(String groupId);

    boolean existsByGroupIdAndUserIdAndIsAdmin(String groupId, String userId, boolean isAdmin);

    boolean existsByIdAndUserIdAndIsAdmin(String id, String userId, boolean isAdmin);

    boolean existsByGroupIdAndUserIdAndIsBanned(String groupId, String userId, boolean isBanned);

    List<GroupMember> getByGroupId(String groupId);

    GroupMember getByGroupIdAndUserId(String groupId, String userId);
}
