package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.Checker;
import org.code4everything.ichat.dao.ContactDAO;
import org.code4everything.ichat.domain.Contact;
import org.code4everything.ichat.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    public boolean isFriend(String inviter, String invitee) {
        boolean isFriend = contactDAO.existsByInviterAndInviteeAndStatus(inviter, invitee, "2");
        return isFriend || contactDAO.existsByInviterAndInviteeAndStatus(invitee, inviter, "2");
    }

    @Override
    public boolean isInviting(String inviter, String invitee) {
        boolean isInviting = contactDAO.existsByInviterAndInviteeAndStatus(inviter, invitee, "1");
        return isInviting || contactDAO.existsByInviterAndInviteeAndStatus(invitee, inviter, "1");
    }

    @Override
    public void save(String inviter, String invitee) {
        Contact contact = contactDAO.findByInviterAndInviteeAndStatus(inviter, invitee, "0");
        if (Checker.isNull(contact)) {
            contact = contactDAO.findByInviterAndInviteeAndStatus(invitee, inviter, "0");
        }
        if (Checker.isNull(contact)) {
            contact = new Contact();
            contact.setCreateTime(System.currentTimeMillis());
            contact.setId(RandomUtil.simpleUUID());
            contact.setInvitee(invitee);
            contact.setInviter(inviter);
            contact.setStatus("1");
            contactDAO.save(contact);
        } else {
            Query query = new Query(Criteria.where("id").is(contact.getId()));
            mongoTemplate.updateFirst(query, new Update().set("status", "1"), Contact.class);
        }
    }
}
