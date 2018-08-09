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
     * 用户编号
     */
    private String userId;

    /**
     * 朋友编号
     */
    private String friendId;

    /**
     * 标签
     */
    private String tag;

    /**
     * 备注名
     */
    private String noteName;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 状态：0拒绝，1等待同意，2同意
     */
    private String status;

    /**
     * 信息更新时间
     */
    private Long updateTime;
}
