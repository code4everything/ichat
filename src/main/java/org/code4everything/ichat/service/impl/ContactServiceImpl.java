package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.Checker;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.dao.ContactDAO;
import org.code4everything.ichat.domain.Contact;
import org.code4everything.ichat.model.ContactDTO;
import org.code4everything.ichat.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pantao
 * @since 2018-08-07
 */
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactDAO contactDAO;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ContactServiceImpl(ContactDAO contactDAO, MongoTemplate mongoTemplate) {
        this.contactDAO = contactDAO;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Contact> list(String userId) {
        return contactDAO.findByUserIdAndStatus(userId, IchatValueConsts.TWO_STR);
    }

    @Override
    public void updateStatus(String userId, String friendId, String status) {
        Query query = new Query(Criteria.where("userId").is(friendId).and("friendId").is(userId));
        Update update = new Update().set("status", status);
        mongoTemplate.updateFirst(query, update, Contact.class);
        if (IchatValueConsts.TWO_STR.equals(status)) {
            save(userId, friendId, status);
        }
    }

    @Override
    public void update(String userId, ContactDTO contact) {
        Query query = new Query(Criteria.where("id").is(contact.getId()).and("userId").is(userId));
        Update update = new Update();
        if (Checker.isNotEmpty(contact.getTag())) {
            update.set("tag", contact.getTag());
        }
        if (Checker.isNotEmpty(contact.getNoteName())) {
            update.set("noteName", contact.getNoteName());
        }
        update.set("updateTime", System.currentTimeMillis());
        mongoTemplate.updateFirst(query, update, Contact.class);
    }

    @Override
    public boolean isFriend(String userId, String friendId) {
        boolean isFriend = contactDAO.existsByUserIdAndFriendIdAndStatus(userId, friendId, "2");
        return isFriend && contactDAO.existsByUserIdAndFriendIdAndStatus(friendId, userId, "2");
    }

    @Override
    public boolean isInvited(String userId, String friendId) {
        return contactDAO.existsByUserIdAndFriendIdAndStatus(userId, friendId, "1");
    }

    @Override
    public void remove(String id, String userId, String friendId) {
        contactDAO.removeByIdAndUserId(id, userId);
        contactDAO.deleteByUserIdAndFriendId(friendId, userId);
    }

    @Override
    public void inviting(String userId, String friendId) {
        Contact contact = contactDAO.findByUserIdAndFriendId(userId, friendId);
        if (Checker.isNull(contact)) {
            save(userId, friendId, IchatValueConsts.ONE_STR);
        } else {
            contact.setStatus(IchatValueConsts.ONE_STR);
            contact.setUpdateTime(System.currentTimeMillis());
            Query query = new Query(Criteria.where("userId").is(userId).and("friendId").is(friendId));
            Update update = new Update();
            update.set("status", IchatValueConsts.ONE_STR).set("updateTime", System.currentTimeMillis());
            mongoTemplate.updateFirst(query, update, Contact.class);
        }
    }

    private void save(String userId, String friendId, String status) {
        Contact contact = new Contact();
        Long time = System.currentTimeMillis();
        contact.setCreateTime(time);
        contact.setId(RandomUtil.simpleUUID());
        contact.setFriendId(friendId);
        contact.setUserId(userId);
        contact.setStatus(status);
        contact.setUpdateTime(time);
        contactDAO.save(contact);
    }
}
