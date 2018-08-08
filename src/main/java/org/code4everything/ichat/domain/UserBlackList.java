package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author pantao
 * @since 2018-08-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBlackList {

    @Id
    private String id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 黑名单用户编号
     */
    private String blackedUserId;

    /**
     * 创建时间
     */
    private Long createTime;
}
