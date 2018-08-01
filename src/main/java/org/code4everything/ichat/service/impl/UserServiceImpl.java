package org.code4everything.ichat.service.impl;

import org.code4everything.ichat.dao.UserDAO;
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
}
