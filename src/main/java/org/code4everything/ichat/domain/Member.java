package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 群组成员
 *
 * @author pantao
 * @since 2018-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private String id;

    /**
     * 组编号
     */
    private String groupId;

    /**
     * 成员编号
     */
    private String userId;

    /**
     * 是否是管理员
     */
    private Boolean isAdmin;

    /**
     * 是否被限制（将不能发言）
     */
    private Boolean isRestricted;

    /**
     * 是否加入黑名单
     */
    private Boolean isBanned;

    /**
     * 加入时间
     */
    private Long createTime;

    /**
     * 是否允许通知
     */
    private Boolean notification;
}
