package org.code4everything.ichat.service;

import com.alibaba.fastjson.JSONObject;
import org.code4everything.ichat.domain.PrivateMessage;

import java.util.List;

/**
 * @author pantao
 * @since 2018/8/29
 */
public interface PrivateMessageService {

    PrivateMessage saveMessage(JSONObject jsonObject);

    List<PrivateMessage> listByUserId(Integer offset, String myId, String userId);

    boolean removeMessage(String messageId, String myId, String userId);
}
