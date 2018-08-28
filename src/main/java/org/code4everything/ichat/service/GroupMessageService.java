package org.code4everything.ichat.service;

import com.alibaba.fastjson.JSONObject;
import org.code4everything.ichat.domain.GroupMessage;

import java.util.List;

/**
 * @author pantao
 * @since 2018/8/28
 */
public interface GroupMessageService {

    GroupMessage saveMessage(JSONObject jsonObject);

    List<GroupMessage> listByGroupId(Integer offset, String groupId);

    boolean removeMessage(String userId, String messageId, String groupId);
}
