package org.code4everything.ichat.service;

import org.code4everything.ichat.domain.Group;
import org.code4everything.ichat.model.GroupDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pantao
 * @since 2018/8/13
 */
public interface GroupService {

    Group newGroup(String userId, GroupDTO groupDTO);

    void dismiss(String userId, String groupId);

    String updateAvatar(String userId, String groupId, MultipartFile avatar);
}
