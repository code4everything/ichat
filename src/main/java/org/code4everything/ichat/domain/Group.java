package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 群组
 *
 * @author pantao
 * @since 2018-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    private String id;

    /**
     * 组名
     */
    private String name;

    /**
     * 组头像
     */
    private String avatar;

    /**
     * 组链接
     */
    private String link;

    /**
     * 关于组
     */
    private String about;

    /**
     * 组类型：1服务，2订阅，3群组
     */
    private String type;

    /**
     * 创建时间
     */
    private Long createTime;
}
