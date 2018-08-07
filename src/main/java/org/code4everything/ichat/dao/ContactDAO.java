package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018-08-07
 */
@Repository
public interface ContactDAO extends MongoRepository<Contact, String> {

    /**
     * 通过指定条件获取 {@link Contact}
     *
     * @param inviter 邀请者
     * @param invitee 被邀请者
     * @param status 状态
     *
     * @return {@link Contact}
     */
    Contact findByInviterAndInviteeAndStatus(String inviter, String invitee, String status);

    /**
     * 验证是否是好友关系
     *
     * @param inviter 邀请者
     * @param invitee 被邀请者
     * @param status 状态
     *
     * @return 是否是好友
     */
    boolean existsByInviterAndInviteeAndStatus(String inviter, String invitee, String status);
}
