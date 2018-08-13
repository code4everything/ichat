package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.ichat.dao.GroupDAO;
import org.code4everything.ichat.domain.Group;
import org.code4everything.ichat.model.GroupDTO;
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

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO) {this.groupDAO = groupDAO;}

    @Override
    public Group save(GroupDTO groupDTO) {
        Group group = new Group(groupDTO);
        group.setId(RandomUtil.simpleUUID());
        group.setCreateTime(System.currentTimeMillis());
        groupDAO.save(group);
        return group;
    }
}
