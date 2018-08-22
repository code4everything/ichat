package org.code4everything.ichat.service.impl;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.encryption.JavaEncrypt;
import org.apache.log4j.Logger;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.dao.UserDAO;
import org.code4everything.ichat.domain.User;
import org.code4everything.ichat.model.BasicUserDTO;
import org.code4everything.ichat.model.SimpleUserVO;
import org.code4everything.ichat.service.CommonService;
import org.code4everything.ichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author pantao
 * @since 2018-08-01
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private static final String CLASS_NAME = UserServiceImpl.class.getName();

    private final UserDAO userDAO;

    private final MongoTemplate mongoTemplate;

    private final CommonService commonService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, MongoTemplate mongoTemplate, CommonService commonService) {
        this.userDAO = userDAO;
        this.mongoTemplate = mongoTemplate;
        this.commonService = commonService;
    }

    @Override
    public boolean existsById(String id) {
        return userDAO.existsById(id);
    }

    @Override
    public String getUsernameById(String id) {
        User user = userDAO.findById(id).get();
        return user.getUsername();
    }

    @Override
    public String getUserIdByUid(String uid) {
        User user = userDAO.findByUid(uid);
        return Checker.isNull(user) ? null : user.getId();
    }

    @Override
    public List<SimpleUserVO> listUserBySearch(String word) {
        Criteria criteria = Criteria.where("username").regex(Pattern.compile(word, Pattern.CASE_INSENSITIVE));
        criteria.orOperator(Criteria.where("email").is(word));
        criteria.orOperator(Criteria.where("phone").is(word));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, SimpleUserVO.class);
    }

    @Override
    public String uploadAvatar(String userId, MultipartFile avatar) {
        String url = commonService.uploadAvatar(userId, avatar);
        if (url.startsWith(IchatValueConsts.AVATAR_MAPPING)) {
            // 头像上传成功
            Query query = new Query(Criteria.where("id").is(userId));
            Update update = new Update().set("avatar", url);
            mongoTemplate.updateFirst(query, update, User.class);
        }
        return url;
    }

    @Override
    public void updatePassword(String id, String password) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("password", JavaEncrypt.MD5.digestHex(password));
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updateUserInfo(String id, BasicUserDTO userInfo) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("username", userInfo.getUsername());
        if (Checker.isNotEmpty(userInfo.getGender()) && IchatValueConsts.GENDER_STRING.contains(userInfo.getGender())) {
            update.set("gender", userInfo.getGender());
        }
        if (Checker.isNotEmpty(userInfo.getBio())) {
            update.set("bio", userInfo.getBio());
        }
        if (Checker.isNotNull(userInfo.getBirth())) {
            update.set("birth", userInfo.getBirth());
        }
        mongoTemplate.updateFirst(query, update, User.class);
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
    public boolean isPasswordCorrect(String id, String password) {
        return userDAO.existsByIdAndPassword(id, JavaEncrypt.MD5.digestHex(password));
    }

    @Override
    public User login(String email, String password) {
        User user = userDAO.findByEmailAndPassword(email, JavaEncrypt.MD5.digestHex(password));
        if (Checker.isNotNull(user)) {
            Query query = new Query(Criteria.where("id").is(user.getId()));
            Update update = new Update();
            update.set("lastLoginTime", System.currentTimeMillis());
            mongoTemplate.updateFirst(query, update, User.class);
        }
        return user;
    }

    @Override
    public Optional<User> getById(String id) {
        return userDAO.findById(id);
    }

    @Override
    public void resetPassword(String email, String password) {
        Query query = new Query(Criteria.where("email").is(email));
        Update update = new Update();
        update.set("password", JavaEncrypt.MD5.digestHex(password));
        mongoTemplate.updateFirst(query, update, User.class);
    }
}
