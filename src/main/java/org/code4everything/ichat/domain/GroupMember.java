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
public class GroupMember {

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

    /**
     * 状态：0拒绝加入，1等待管理员同意，2等待用户同意，3同意加入，4普通用户邀请（需等待用户同意），5普通用户邀请（无需用户同意）
     * <p>
     * 普通用户邀请：待用户同意后需等待管理员同意
     * </p>
     */
    private String status;
}
