package org.code4everything.ichat.model;

import com.zhazhapan.util.Checker;
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

    private String username;

    private String gender;

    private String phone;

    private String email;

    private String avatar;

    private Long birth;

    private String bio;

    private Long lastLoginTime;

    public UserVO(User user) {
        if (Checker.isNotNull(user)) {
            username = user.getUsername();
            gender = user.getGender();
            phone = user.getPhone();
            email = user.getEmail();
            avatar = user.getAvatar();
            birth = user.getBirth();
            bio = user.getBio();
            lastLoginTime = user.getLastLoginTime();
        }
    }
}
