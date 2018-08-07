package org.code4everything.ichat.model;

import com.zhazhapan.util.Checker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.ichat.domain.User;

/**
 * @author pantao
 * @since 2018-08-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserVO {

    private String uid;

    private String username;

    private String avatar;

    private String bio;

    public SimpleUserVO(User user) {
        if (Checker.isNotNull(user)) {
            uid = user.getUid();
            username = user.getUsername();
            avatar = user.getAvatar();
            bio = user.getBio();
        }
    }
}
