package org.code4everything.ichat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.ichat.domain.User;

/**
 * @author pantao
 * @since 2018-08-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别
     */
    private String gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 出生日期
     */
    private Long birth;

    /**
     * 个人简介
     */
    private String bio;

    public UserVO(User user) {
        username = user.getUsername();
        gender = user.getGender();
        phone = user.getPhone();
        email = user.getEmail();
        avatar = user.getAvatar();
        birth = user.getBirth();
        bio = user.getBio();
    }
}
