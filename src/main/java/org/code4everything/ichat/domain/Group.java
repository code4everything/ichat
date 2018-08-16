package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.code4everything.ichat.model.GroupDTO;
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
     * 创建者（用户编号）
     */
    private String creator;

    /**
     * 是否私有：0公开（允许任何人加入群），1私有（需管理员同意后方可加入）
     */
    private String isPrivate;

    /**
     * 创建时间
     */
    private Long createTime;

    public Group(GroupDTO groupDTO) {
        name = groupDTO.getName();
        avatar = groupDTO.getAvatar();
        link = groupDTO.getLink();
        about = groupDTO.getAbout();
        type = groupDTO.getType();
        isPrivate = groupDTO.getIsPrivate();
    }
}
