package org.code4everything.ichat.service;

import org.code4everything.ichat.domain.Contact;
import org.code4everything.ichat.model.ContactDTO;

import java.util.List;

/**
 * @author pantao
 * @since 2018-08-07
 */
public interface ContactService {

    List<Contact> list(String userId);

    void updateStatus(String userId, String friendId, String noteName, String status);

    void update(String userId, ContactDTO contact);

    boolean isFriend(String userId, String friendId);

    boolean isInvited(String userId, String friendId);

    void remove(String id, String userId, String friendId);

    void inviting(String userId, String friendId, String noteName);
}
