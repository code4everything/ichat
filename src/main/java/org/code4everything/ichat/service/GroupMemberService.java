package org.code4everything.ichat.service;

import org.code4everything.ichat.domain.GroupMember;

/**
 * @author pantao
 * @since 2018/8/16
 */
public interface GroupMemberService {

    GroupMember addMember(String groupId, String userId);

    GroupMember addGroupAdmin(String groupId, String userId);
}
