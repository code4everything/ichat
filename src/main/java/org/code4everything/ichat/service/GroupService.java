package org.code4everything.ichat.service;

import org.code4everything.ichat.domain.Group;
import org.code4everything.ichat.model.GroupDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pantao
 * @since 2018/8/13
 */
public interface GroupService {

    boolean linkExists(String link);

    Group newGroup(String userId, GroupDTO groupDTO);

    void dismiss(String userId, String groupId);

    void updateInfo(String userId, String groupId, String name, String about);

    void updateLink(String userId, String groupId, String link);

    String updateAvatar(String userId, String groupId, MultipartFile avatar);

    void updateType(String userId, String groupId, String type);

    void updateCreator(String originalCreator, String groupId, String newCreator);
}
