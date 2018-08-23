package org.code4everything.ichat.service;

import org.code4everything.ichat.domain.GroupMember;

/**
 * @author pantao
 * @since 2018/8/16
 */
public interface GroupMemberService {

    GroupMember addMember(String groupId, String userId);

    boolean agree(String myId, String uid, String groupId);

    boolean inviteMember(String myId, String groupId, String uid);

    boolean isBanned(String groupId, String userId);

    boolean joinGroup(String userId, String link);

    boolean isAdmin(String userId, String groupId);

    GroupMember addMemberByLink(String link, String userId);

    GroupMember addGroupAdmin(String groupId, String userId);
}
