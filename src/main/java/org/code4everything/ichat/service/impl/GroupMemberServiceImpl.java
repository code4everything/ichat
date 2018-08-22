package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.Checker;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.dao.*;
import org.code4everything.ichat.domain.Group;
import org.code4everything.ichat.domain.GroupMember;
import org.code4everything.ichat.domain.User;
import org.code4everything.ichat.domain.UserSetting;
import org.code4everything.ichat.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018/8/16
 */
@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    private final GroupMemberDAO groupMemberDAO;

    private final GroupDAO groupDAO;

    private final UserDAO userDAO;

    private final UserSettingDAO userSettingDAO;

    private final ContactDAO contactDAO;

    @Autowired
    public GroupMemberServiceImpl(GroupMemberDAO groupMemberDAO, GroupDAO groupDAO, UserDAO userDAO,
                                  UserSettingDAO userSettingDAO, ContactDAO contactDAO) {
        this.groupMemberDAO = groupMemberDAO;
        this.groupDAO = groupDAO;
        this.userDAO = userDAO;
        this.userSettingDAO = userSettingDAO;
        this.contactDAO = contactDAO;
    }

    @Override
    public GroupMember addMember(String groupId, String userId) {
        return groupMemberDAO.save(getDefaultMember(groupId, userId));
    }

    @Override
    public boolean inviteMember(String myId, String groupId, String uid) {
        GroupMember member = groupMemberDAO.getByGroupIdAndUserId(groupId, myId);
        if (Checker.isNotNull(member)) {
            User user = getUserByUid(uid);
            if (Checker.isNotNull(user) && !isBanned(groupId, user.getId())) {
                UserSetting setting = userSettingDAO.getByUserId(user.getId());
                GroupMember newMember = groupMemberDAO.getByGroupIdAndUserId(groupId, user.getId());
                if (Checker.isNull(newMember)) {
                    newMember = getDefaultMember(groupId, user.getId());
                }
                if (member.getIsAdmin()) {
                    if (IchatValueConsts.ZERO_STR.equals(setting.getInvitation())) {
                        newMember.setStatus("2");
                    } else if (IchatValueConsts.ONE_STR.equals(setting.getInvitation())) {
                        if (contactDAO.existsByUserIdAndFriendIdAndStatus(user.getId(), myId,
                                IchatValueConsts.TWO_STR)) {
                            newMember.setStatus("3");
                        } else {
                            newMember.setStatus("2");
                        }
                    } else {
                        newMember.setStatus("3");
                    }
                } else {
                    if (IchatValueConsts.ZERO_STR.equals(setting.getInvitation())) {
                        newMember.setStatus("4");
                    } else if (IchatValueConsts.ONE_STR.equals(setting.getInvitation())) {
                        if (contactDAO.existsByUserIdAndFriendIdAndStatus(user.getId(), myId,
                                IchatValueConsts.TWO_STR)) {
                            newMember.setStatus("5");
                        } else {
                            newMember.setStatus("4");
                        }
                    } else {
                        newMember.setStatus("5");
                    }
                }
                groupMemberDAO.save(newMember);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isBanned(String groupId, String userId) {
        return groupMemberDAO.existsByGroupIdAndUserIdAndIsBanned(groupId, userId, true);
    }

    @Override
    public boolean joinGroup(String userId, String link) {
        Group group = groupDAO.getGroupByLink(link);
        if (Checker.isNotNull(group) && !isBanned(group.getId(), userId)) {
            GroupMember member = groupMemberDAO.getByGroupIdAndUserId(group.getId(), userId);
            if (Checker.isNull(member)) {
                member = getDefaultMember(group.getId(), userId);
            }
            if (IchatValueConsts.ONE_STR.equals(group.getIsPrivate())) {
                // 私有的群组，状态设置为等待管理员同意
                member.setStatus("1");
            }
            groupMemberDAO.save(member);
            return true;
        }
        return false;
    }

    @Override
    public boolean isAdmin(String userId, String groupId) {
        return groupMemberDAO.existsByGroupIdAndUserIdAndIsAdmin(groupId, userId, true);
    }

    @Override
    public GroupMember addMemberByLink(String link, String userId) {
        Group group = groupDAO.getGroupByLink(link);
        return Checker.isNull(group) ? null : addMember(group.getId(), userId);
    }

    @Override
    public GroupMember addGroupAdmin(String groupId, String userId) {
        GroupMember member = getDefaultMember(groupId, userId);
        member.setIsAdmin(true);
        return groupMemberDAO.save(member);
    }

    private GroupMember getDefaultMember(String groupId, String userId) {
        GroupMember member = new GroupMember();
        member.setCreateTime(System.currentTimeMillis());
        member.setGroupId(groupId);
        member.setId(RandomUtil.simpleUUID());
        member.setIsBanned(false);
        member.setIsRestricted(false);
        member.setNotification(true);
        member.setUserId(userId);
        member.setStatus("3");
        member.setIsAdmin(false);
        return member;
    }

    private User getUserByUid(String uid) {
        User user = userDAO.findByUid(uid);
        if (Checker.isNull(user)) {
            user = userDAO.getUserByPhone(uid);
            if (Checker.isNull(user)) {
                user = userDAO.getUserByEmail(uid);
                if (Checker.isNull(user)) {
                    user = userDAO.getUserById(uid);
                }
            }
        }
        return user;
    }
}
