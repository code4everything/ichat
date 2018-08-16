package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.ichat.dao.GroupMemberDAO;
import org.code4everything.ichat.domain.GroupMember;
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

    @Autowired
    public GroupMemberServiceImpl(GroupMemberDAO groupMemberDAO) {this.groupMemberDAO = groupMemberDAO;}

    @Override
    public GroupMember addMember(String groupId, String userId) {
        GroupMember member = getDefaultMember(groupId, userId);
        member.setIsAdmin(false);
        groupMemberDAO.save(member);
        return member;
    }

    @Override
    public GroupMember addGroupAdmin(String groupId, String userId) {
        GroupMember member = getDefaultMember(groupId, userId);
        member.setIsAdmin(true);
        groupMemberDAO.save(member);
        return member;
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
        return member;
    }
}
