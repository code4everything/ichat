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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private final MongoTemplate mongoTemplate;

    @Autowired
    public GroupMemberServiceImpl(GroupMemberDAO groupMemberDAO, GroupDAO groupDAO, UserDAO userDAO,
                                  UserSettingDAO userSettingDAO, ContactDAO contactDAO, MongoTemplate mongoTemplate) {
        this.groupMemberDAO = groupMemberDAO;
        this.groupDAO = groupDAO;
        this.userDAO = userDAO;
        this.userSettingDAO = userSettingDAO;
        this.contactDAO = contactDAO;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<GroupMember> listGroupMember(String groupId) {
        return groupMemberDAO.getByGroupId(groupId);
    }

    @Override
    public GroupMember addMember(String groupId, String userId) {
        return groupMemberDAO.save(getDefaultMember(groupId, userId));
    }

    @Override
    public boolean setBanned(String myId, String groupId, String userId, String banned) {
        return updateBoolean(myId, groupId, userId, "isBanned", banned);
    }

    @Override
    public boolean setRestricted(String myId, String groupId, String userId, String restricted) {
        return updateBoolean(myId, groupId, userId, "isRestricted", restricted);
    }

    @Override
    public void setNotification(String groupId, String userId, String notification) {
        Query query = new Query(Criteria.where("groupId").is(groupId).and("userId").is(userId));
        Update update = new Update().set("notification", IchatValueConsts.ONE_STR.equals(notification));
        mongoTemplate.updateFirst(query, update, GroupMember.class);
    }

    @Override
    public void toggleAdminMode(String groupId, String userId, String isAdmin) {
        Query query = new Query(Criteria.where("groupId").is(groupId).and("userId").is(userId));
        Update update = new Update().set("isAdmin", IchatValueConsts.ONE_STR.equals(isAdmin));
        mongoTemplate.updateFirst(query, update, GroupMember.class);
    }

    @Override
    public boolean isCreator(String userId, String groupId) {
        return groupDAO.existsByIdAndCreator(groupId, userId);
    }

    @Override
    public boolean agree(String myId, String uid, String groupId) {
        User user = getUserByUid(uid);
        if (Checker.isNotNull(user)) {
            GroupMember member = groupMemberDAO.getByGroupIdAndUserId(groupId, user.getId());
            if (Checker.isNotNull(member)) {
                switch (member.getStatus()) {
                    case IchatValueConsts.ONE_STR:
                        // 管理员同意
                        if (isAdmin(myId, groupId)) {
                            member.setStatus("3");
                        }
                        break;
                    case IchatValueConsts.TWO_STR:
                        // 用户同意
                        member.setStatus("3");
                        break;
                    case IchatValueConsts.FOUR_STR:
                        member.setStatus("1");
                        break;
                    case IchatValueConsts.FIVE_STR:
                        member.setStatus("1");
                        break;
                    default:
                        break;
                }
                groupMemberDAO.save(member);
                return true;
            }
        }
        return false;
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
                } else if (IchatValueConsts.THREE_STR.equals(newMember.getStatus())) {
                    // 已经存在无需邀请
                    return false;
                }
                if (member.getIsAdmin()) {
                    setMemberStatus(myId, newMember, user.getId(), "2", "3", setting.getInvitation());
                } else {
                    setMemberStatus(myId, newMember, user.getId(), "4", "5", setting.getInvitation());
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
            } else {
                member.setStatus("3");
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

    private boolean updateBoolean(String myId, String groupId, String userId, String field, String value) {
        if (isAdmin(myId, groupId) && !isAdmin(userId, groupId)) {
            Query query = new Query(Criteria.where("groupId").is(groupId).and("userId").is(userId));
            Update update = new Update();
            update.set(field, IchatValueConsts.ONE_STR.equals(value));
            mongoTemplate.updateFirst(query, update, GroupMember.class);
            return false;
        }
        return false;
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

    private void setMemberStatus(String myId, GroupMember member, String userId, String aStatus, String bStatus,
                                 String invitation) {
        if (IchatValueConsts.ZERO_STR.equals(invitation)) {
            member.setStatus(aStatus);
        } else if (IchatValueConsts.ONE_STR.equals(invitation)) {
            if (contactDAO.existsByUserIdAndFriendIdAndStatus(userId, myId, IchatValueConsts.TWO_STR)) {
                member.setStatus(bStatus);
            } else {
                member.setStatus(aStatus);
            }
        } else {
            member.setStatus(bStatus);
        }
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
