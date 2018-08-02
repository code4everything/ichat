package org.code4everything.ichat.service.impl;

import com.zhazhapan.util.encryption.JavaEncrypt;
import org.code4everything.ichat.dao.UserDAO;
import org.code4everything.ichat.domain.User;
import org.code4everything.ichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018-08-01
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {this.userDAO = userDAO;}

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

    }
}
