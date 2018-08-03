package org.code4everything.ichat.service.impl;

import com.zhazhapan.util.encryption.JavaEncrypt;
import org.code4everything.ichat.dao.UserDAO;
import org.code4everything.ichat.domain.User;
import org.code4everything.ichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018-08-01
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, MongoTemplate mongoTemplate) {
        this.userDAO = userDAO;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean emailExists(String email) {
        return 1 == userDAO.countByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userDAO.save(user);
    }

    @Override
    public User login(String email, String password) {
        return userDAO.findByEmailAndPassword(email, JavaEncrypt.MD5.digestHex(password));
    }

    @Override
    public void resetPassword(String email, String password) {
        Query query = new Query(Criteria.where("email").is(email));
        Update update = new Update();
        update.set("password", JavaEncrypt.MD5.digest(password));
        mongoTemplate.updateFirst(query, update, User.class);
    }
}
