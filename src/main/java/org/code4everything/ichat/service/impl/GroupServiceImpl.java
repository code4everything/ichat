package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.dao.GroupDAO;
import org.code4everything.ichat.dao.GroupMemberDAO;
import org.code4everything.ichat.domain.Group;
import org.code4everything.ichat.model.GroupDTO;
import org.code4everything.ichat.service.CommonService;
import org.code4everything.ichat.service.GroupMemberService;
import org.code4everything.ichat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pantao
 * @since 2018/8/13
 */
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDAO groupDAO;

    private final GroupMemberDAO groupMemberDAO;

    private final GroupMemberService groupMemberService;

    private final CommonService commonService;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO, GroupMemberService groupMemberService, GroupMemberDAO groupMemberDAO,
                            CommonService commonService, MongoTemplate mongoTemplate) {
        this.groupDAO = groupDAO;
        this.groupMemberService = groupMemberService;
        this.groupMemberDAO = groupMemberDAO;
        this.commonService = commonService;
        this.mongoTemplate = mongoTemplate;
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

    @Override
    public void dismiss(String userId, String groupId) {
        boolean result = groupDAO.deleteByIdAndCreator(groupId, userId);
        if (result) {
            groupMemberDAO.deleteByGroupId(groupId);
        }
    }

    @Override
    public String updateAvatar(String userId, String groupId, MultipartFile avatar) {
        String url = commonService.uploadAvatar(userId, avatar);
        if (url.startsWith(IchatValueConsts.AVATAR_MAPPING)) {
            // 头像上传成功
            Query query = new Query(Criteria.where("id").is(groupId).and("creator").is(userId));
            Update update = new Update().set("avatar", url);
            mongoTemplate.updateFirst(query, update, Group.class);
        }
        return url;
    }

    @Override
    public void updateType(String userId, String groupId, String type) {
        Query query = new Query(Criteria.where("id").is(groupId).and("creator").is(userId));
        mongoTemplate.updateFirst(query, new Update().set("isPrivate", type), Group.class);
    }

    @Override
    public void updateCreator(String originalCreator, String groupId, String newCreator) {
        Query query = new Query(Criteria.where("id").is(groupId).and("creator").is(originalCreator));
        Update update = new Update().set("creator", newCreator);
        mongoTemplate.updateFirst(query, update, Group.class);
    }
}
