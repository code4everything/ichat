package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 用户设置
 *
 * @author pantao
 * @since 2018-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

    @Id
    private String id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 是否允许和陌生人聊天
     */
    private Boolean withStranger;

    /**
     * 是否允许好友添加
     */
    private Boolean isAddable;

    /**
     * 是否向好友公开个人信息
     */
    private Boolean isVisible;

    /**
     * 公开显示性别
     */
    private Boolean isGenderVisible;

    /**
     * 公开显示手机号
     */
    private Boolean isPhoneVisible;

    /**
     * 公开显示邮箱
     */
    private Boolean isEmailVisible;

    /**
     * 公开显示年龄
     */
    private Boolean isAgeVisible;

    /**
     * 群邀请设置：0永远不允许，1好友允许，2所有人允许
     */
    private String invitation;

    /**
     * 更新时间
     */
    private Long updateTime;
}
