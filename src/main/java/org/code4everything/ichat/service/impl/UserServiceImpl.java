package org.code4everything.ichat.service.impl;

import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.encryption.JavaEncrypt;
import com.zhazhapan.util.enums.LogLevel;
import org.apache.log4j.Logger;
import org.code4everything.ichat.constant.ConfigConsts;
import org.code4everything.ichat.constant.DefaultConfigValueConsts;
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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    public List<SimpleUserVO> listUserBySearch(String word) {
        Criteria criteria = Criteria.where("username").regex(Pattern.compile(word, Pattern.CASE_INSENSITIVE));
        criteria.orOperator(Criteria.where("email").is(word));
        criteria.orOperator(Criteria.where("phone").is(word));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, SimpleUserVO.class);
    }

    @Override
    public String uploadAvatar(String userId, MultipartFile avatar) {
        if (Checker.isNull(avatar) || avatar.getSize() > ValueConsts.MB) {
            return "头像大小不能超过1MB";
        }
        String local = Checker.checkNull(commonService.getString(ConfigConsts.FILE_STORAGE_PATH),
                DefaultConfigValueConsts.FILE_STORAGE_PATH);
        String md5;
        try {
            md5 = Arrays.toString(JavaEncrypt.MD5.digest(avatar.getBytes()));
        } catch (IOException e) {
            logger.error(e.getMessage());
            String msg = "get md5 from avatar error: " + e.getMessage();
            commonService.saveLog(LogLevel.ERROR, CLASS_NAME + "#uploadAvatar", msg, userId);
            return "内部错误：获取文件MD5码失败";
        }
        String suffix = FileExecutor.getFileSuffix(Objects.requireNonNull(avatar.getOriginalFilename()));
        String filename = md5 + ValueConsts.DOT_SIGN + suffix;
        local += (local.endsWith(File.separator) ? "" : File.separator) + filename;
        String url = IchatValueConsts.AVATAR_MAPPING + filename;
        url = commonService.saveDocument(local, url, avatar, userId);
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
    public void resetPassword(String email, String password) {
        Query query = new Query(Criteria.where("email").is(email));
        Update update = new Update();
        update.set("password", JavaEncrypt.MD5.digestHex(password));
        mongoTemplate.updateFirst(query, update, User.class);
    }
}
