package org.code4everything.ichat.service;

import org.code4everything.ichat.model.ContactDTO;

/**
 * @author pantao
 * @since 2018-08-07
 */
public interface ContactService {

    void updateStatus(String userId, String friendId, String status);

    void update(String userId, ContactDTO contact);

    boolean isFriend(String userId, String friendId);

    boolean isInvited(String userId, String friendId);

    void remove(String id, String userId, String friendId);

    void inviting(String userId, String friendId);
}
