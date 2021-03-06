package org.code4everything.ichat.domain;

import cn.hutool.core.util.RandomUtil;
import com.zhazhapan.util.encryption.JavaEncrypt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.ichat.model.BasicUserDTO;
import org.code4everything.ichat.model.RegisterDTO;
import org.springframework.data.annotation.Id;

/**
 * 用户
 *
 * @author pantao
 * @since 2018-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

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
     * 密码
     */
    private String password;

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

    /**
     * 账号状态：0冻结，1正常，2管理员（可登陆后台管理系统），3系统账号（第一个注册的用户自动获取该身份）
     */
    private String status;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 最近一次登录时间
     */
    private Long lastLoginTime;

    /**
     * 唯一标识符
     */
    private String uid;

    public User(RegisterDTO register) {
        username = register.getUsername();
        email = register.getEmail();
        password = JavaEncrypt.MD5.digestHex(register.getPassword());
        setDefaultValue();
    }

    public User setUserInfo(BasicUserDTO userInfo) {
        username = userInfo.getUsername();
        gender = userInfo.getGender();
        bio = userInfo.getBio();
        birth = userInfo.getBirth();
        return this;
    }

    private void setDefaultValue() {
        createTime = System.currentTimeMillis();
        status = "1";
        id = RandomUtil.simpleUUID();
        uid = JavaEncrypt.MD5.digestHex(email + String.valueOf(createTime));
    }
}
