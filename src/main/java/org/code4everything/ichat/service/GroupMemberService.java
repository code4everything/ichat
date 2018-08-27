package org.code4everything.ichat.service;

import org.code4everything.ichat.domain.GroupMember;

import java.util.List;

/**
 * @author pantao
 * @since 2018/8/16
 */
public interface GroupMemberService {

    List<GroupMember> listGroupMember(String groupId);

    GroupMember addMember(String groupId, String userId);

    boolean setBanned(String myId, String groupId, String userId, String banned);

    boolean setRestricted(String myId, String groupId, String userId, String restricted);

    void setNotification(String groupId, String userId, String notification);

    void toggleAdminMode(String groupId, String userId, String isAdmin);

    boolean isCreator(String userId, String groupId);

    boolean agree(String myId, String uid, String groupId);

    boolean inviteMember(String myId, String groupId, String uid);

    boolean isBanned(String groupId, String userId);

    boolean joinGroup(String userId, String link);

    boolean isAdmin(String userId, String groupId);

    GroupMember addMemberByLink(String link, String userId);

    GroupMember addGroupAdmin(String groupId, String userId);
}
