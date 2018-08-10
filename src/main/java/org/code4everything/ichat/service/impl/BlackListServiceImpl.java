package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.Checker;
import org.code4everything.ichat.dao.BlackListDAO;
import org.code4everything.ichat.domain.BlackList;
import org.code4everything.ichat.service.BlackListService;
import org.code4everything.ichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018-08-09
 */
@Service
public class BlackListServiceImpl implements BlackListService {

    private final BlackListDAO blackListDAO;

    private final UserService userService;

    @Autowired
    public BlackListServiceImpl(BlackListDAO blackListDAO, UserService userService) {
        this.blackListDAO = blackListDAO;
        this.userService = userService;
    }

    @Override
    public boolean isBanned(String userId, String anotherUserId) {
        return blackListDAO.existsByUserIdAndBlackedUserId(userId, anotherUserId) || blackListDAO.existsByUserIdAndBlackedUserId(anotherUserId, userId);
    }

    @Override
    public void delete(String userId, String id) {
        blackListDAO.deleteByIdAndUserId(id, userId);
    }

    @Override
    public void addToBlackList(String userId, String uid) {
        String anotherUserId = userService.getUserIdByUid(uid);
        if (Checker.isNotEmpty(anotherUserId) && !isBanned(userId, anotherUserId)) {
            BlackList list = new BlackList(RandomUtil.simpleUUID(), userId, anotherUserId, System.currentTimeMillis());
            blackListDAO.save(list);
        }
    }
}
