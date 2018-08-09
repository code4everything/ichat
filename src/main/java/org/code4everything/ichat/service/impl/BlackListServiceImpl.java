package org.code4everything.ichat.service.impl;

import org.code4everything.ichat.dao.BlackListDAO;
import org.code4everything.ichat.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018-08-09
 */
@Service
public class BlackListServiceImpl implements BlackListService {

    private final BlackListDAO blackListDAO;

    @Autowired
    public BlackListServiceImpl(BlackListDAO blackListDAO) {this.blackListDAO = blackListDAO;}

    @Override
    public boolean isBanned(String userId, String anotherUserId) {
        return blackListDAO.existsByUserIdAndBlackedUserId(userId, anotherUserId) || blackListDAO.existsByUserIdAndBlackedUserId(anotherUserId, userId);
    }
}
