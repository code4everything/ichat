package org.code4everything.ichat.service;

/**
 * @author pantao
 * @since 2018-08-07
 */
public interface ContactService {

    boolean isFriend(String inviter, String invitee);

    boolean isInviting(String inviter, String invitee);

    void save(String inviter, String invitee);
}
