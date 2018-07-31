package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 联系人列表
 *
 * @author pantao
 * @since 2018-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    private String id;

    /**
     * 邀请者
     */
    private String inviter;

    /**
     * 被邀请者
     */
    private String invitee;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 状态：0拒绝，1等待同意，2同意
     */
    private String status;
}
