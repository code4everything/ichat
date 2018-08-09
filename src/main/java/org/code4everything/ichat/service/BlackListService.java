package org.code4everything.ichat.service;

/**
 * @author pantao
 * @since 2018-08-09
 */
public interface BlackListService {

    boolean isBanned(String userId, String anotherUserId);
}
