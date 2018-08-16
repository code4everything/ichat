package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.ichat.dao.GroupDAO;
import org.code4everything.ichat.domain.Group;
import org.code4everything.ichat.model.GroupDTO;
import org.code4everything.ichat.service.GroupMemberService;
import org.code4everything.ichat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018/8/13
 */
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDAO groupDAO;

    private final GroupMemberService groupMemberService;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO, GroupMemberService groupMemberService) {
        this.groupDAO = groupDAO;
        this.groupMemberService = groupMemberService;
    }

    @Override
    public Group newGroup(String userId, GroupDTO groupDTO) {
        Group group = new Group(groupDTO);
        Long timestamp = System.currentTimeMillis();
        group.setId(RandomUtil.simpleUUID());
        group.setCreateTime(timestamp);
        group.setCreator(userId);
        groupDAO.save(group);
        groupMemberService.addGroupAdmin(group.getId(), userId);
        return group;
    }
}
