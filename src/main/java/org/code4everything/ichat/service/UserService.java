package org.code4everything.ichat.service;

import org.code4everything.ichat.domain.User;
import org.code4everything.ichat.model.BasicUserDTO;
import org.code4everything.ichat.model.SimpleUserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author pantao
 * @since 2018-08-01
 */
public interface UserService {

    boolean existsById(String id);

    String getUsernameById(String id);

    String getUserIdByUid(String uid);

    List<SimpleUserVO> listUserBySearch(String word);

    String uploadAvatar(String userId, MultipartFile avatar);

    void updatePassword(String id, String password);

    void updateUserInfo(String id, BasicUserDTO userInfo);

    /**
     * 验证邮箱是否存在
     *
     * @param email 有限
     *
     * @return 是否存在
     */
    boolean emailExists(String email);

    void saveUser(User user);

    boolean isPasswordCorrect(String id, String password);

    User login(String email, String password);

    Optional<User> getById(String id);

    void resetPassword(String email, String password);
}
