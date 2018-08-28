package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.Formatter;
import org.code4everything.ichat.constant.ConfigConsts;
import org.code4everything.ichat.constant.DefaultConfigValueConsts;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.dao.GroupMessageDAO;
import org.code4everything.ichat.domain.GroupMessage;
import org.code4everything.ichat.service.CommonService;
import org.code4everything.ichat.service.GroupMemberService;
import org.code4everything.ichat.service.GroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pantao
 * @since 2018/8/28
 */
@Service
public class GroupMessageServiceImpl implements GroupMessageService {

    private final GroupMessageDAO groupMessageDAO;

    private final MongoTemplate mongoTemplate;

    private final GroupMemberService groupMemberService;

    private final CommonService commonService;

    @Autowired
    public GroupMessageServiceImpl(GroupMessageDAO groupMessageDAO, MongoTemplate mongoTemplate,
                                   GroupMemberService groupMemberService, CommonService commonService) {
        this.groupMessageDAO = groupMessageDAO;
        this.mongoTemplate = mongoTemplate;
        this.groupMemberService = groupMemberService;
        this.commonService = commonService;
    }

    @Override
    public GroupMessage saveMessage(JSONObject jsonObject) {
        GroupMessage message = parseJsonObject(jsonObject);
        if (Checker.isNotNull(message)) {
            groupMessageDAO.save(message);
        }
        return message;
    }

    @Override
    public List<GroupMessage> listByGroupId(Integer offset, String groupId) {
        Query query = new Query(Criteria.where("to").is(groupId));
        query.skip(offset);
        String pageSize = commonService.getString(ConfigConsts.MESSAGE_PAGE_SIZE);
        query.limit(Checker.isEmpty(pageSize) ? DefaultConfigValueConsts.MESSAGE_PAGE_SIZE :
                Formatter.stringToInt(pageSize));
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        return mongoTemplate.find(query, GroupMessage.class);
    }

    @Override
    public boolean removeMessage(String userId, String messageId, String groupId) {
        GroupMessage message = groupMessageDAO.findById(messageId).orElse(null);
        if (Checker.isNotNull(message)) {
            Query query = new Query(Criteria.where("id").is(messageId).and("to").is(groupId));
            Update update = new Update().set("isDeleted", IchatValueConsts.ONE_STR);
            // 是否是自己的消息
            boolean canDelete = message.getFrom().equals(userId);
            if (!canDelete) {
                // 不是自己的消息，验证权限
                boolean iAmAdmin = groupMemberService.isAdmin(userId, groupId);
                boolean heIsAdmin = groupMemberService.isAdmin(message.getFrom(), groupId);
                canDelete = heIsAdmin ? groupMemberService.isCreator(userId, groupId) : iAmAdmin;
            }
            if (canDelete) {
                mongoTemplate.updateFirst(query, update, GroupMessage.class);
                return true;
            }
        }
        return false;
    }

    private GroupMessage parseJsonObject(JSONObject jsonObject) {
        GroupMessage message = new GroupMessage();
        message.setCreateTime(System.currentTimeMillis());
        message.setFrom(jsonObject.getString("from"));
        if (Checker.isEmpty(message.getFrom())) {
            return null;
        }
        message.setId(RandomUtil.simpleUUID());
        message.setIsDeleted(IchatValueConsts.ZERO_STR);
        message.setMessage(jsonObject.getString("message"));
        if (Checker.isEmpty(message.getMessage())) {
            return null;
        }
        message.setRepliedId(jsonObject.getString("repliedId"));
        message.setTo(jsonObject.getString("to"));
        if (Checker.isEmpty(message.getTo())) {
            return null;
        }
        return message;
    }
}
