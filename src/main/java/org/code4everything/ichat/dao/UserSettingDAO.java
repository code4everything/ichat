package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.UserSetting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/8/22
 */
@Repository
public interface UserSettingDAO extends MongoRepository<UserSetting, String> {

    UserSetting getByUserId(String userId);
}
