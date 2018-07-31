package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 登陆日志
 *
 * @author pantao
 * @since 2018-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Id
    private String id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 登陆时间
     */
    private Long time;

    /**
     * 登陆IP，存储二进制数据
     */
    private String ip;

    /**
     * 登陆地址
     */
    private String location;
}
