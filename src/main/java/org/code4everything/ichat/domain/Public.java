package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 群组消息
 *
 * @author pantao
 * @since 2018-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Public {

    @Id
    private String id;

    /**
     * 发送者（用户编号）
     */
    private String from;

    /**
     * 接收者（群组编号）
     */
    private String to;

    /**
     * 发送时间
     */
    private Long createTime;

    /**
     * 消息
     */
    private String message;

    /**
     * 是否被删除
     */
    private String isDeleted;

    /**
     * 回复的消息编号
     */
    private String reply;
}
