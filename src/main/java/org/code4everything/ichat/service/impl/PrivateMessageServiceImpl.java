package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.Formatter;
import org.code4everything.ichat.constant.ConfigConsts;
import org.code4everything.ichat.constant.DefaultConfigValueConsts;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.dao.PrivateMessageDAO;
import org.code4everything.ichat.domain.PrivateMessage;
import org.code4everything.ichat.service.BlackListService;
import org.code4everything.ichat.service.CommonService;
import org.code4everything.ichat.service.ContactService;
import org.code4everything.ichat.service.PrivateMessageService;
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
 * @since 2018/8/29
 */
@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {

    private final MongoTemplate mongoTemplate;

    private final CommonService commonService;

    private final PrivateMessageDAO privateMessageDAO;

    private final BlackListService blackListService;

    private final ContactService contactService;

    @Autowired
    public PrivateMessageServiceImpl(MongoTemplate mongoTemplate, CommonService commonService,
                                     PrivateMessageDAO privateMessageDAO, BlackListService blackListService,
                                     ContactService contactService) {
        this.mongoTemplate = mongoTemplate;
        this.commonService = commonService;
        this.privateMessageDAO = privateMessageDAO;
        this.blackListService = blackListService;
        this.contactService = contactService;
    }

    @Override
    public PrivateMessage saveMessage(JSONObject jsonObject) {
        PrivateMessage message = parseJsonObject(jsonObject);
        if (Checker.isNotNull(message)) {
            // 检测我是否在对方的好久列表中
            if (!contactService.isFriend(message.getTo(), message.getFrom())) {
                // 检测我是否在对方黑名单中
                if (blackListService.isBanned(message.getTo(), message.getFrom())) {
                    return null;
                }
                // 是否允许陌生人发起聊天
                if (!contactService.shouldStrangerChat(message.getTo())) {
                    return null;
                }
            }
            privateMessageDAO.save(message);
        }
        return message;
    }

    @Override
    public List<PrivateMessage> listByUserId(Integer offset, String myId, String userId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("from").is(myId).and("to").is(userId);
        criteria.orOperator(Criteria.where("from").is(userId).and("to").is(myId));
        query.addCriteria(criteria);
        query.skip(offset);
        String pageSize = commonService.getString(ConfigConsts.MESSAGE_PAGE_SIZE);
        query.limit(Checker.isEmpty(pageSize) ? DefaultConfigValueConsts.MESSAGE_PAGE_SIZE :
                Formatter.stringToInt(pageSize));
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        return mongoTemplate.find(query, PrivateMessage.class);
    }

    @Override
    public boolean removeMessage(String messageId, String myId, String userId) {
        PrivateMessage message = privateMessageDAO.findById(messageId).orElse(null);
        if (Checker.isNotNull(message) && myId.equals(userId)) {
            Query query = new Query(Criteria.where("id").is(messageId).and("from").is(myId));
            Update update = new Update().set("isDeleted", IchatValueConsts.ONE_STR);
            mongoTemplate.updateFirst(query, update, PrivateMessage.class);
            return true;
        }
        return false;
    }

    private PrivateMessage parseJsonObject(JSONObject jsonObject) {
        PrivateMessage message = new PrivateMessage();
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
