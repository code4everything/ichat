package org.code4everything.ichat.model;

import com.zhazhapan.util.annotation.FieldChecking;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pantao
 * @since 2018/8/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "group", description = "新建群组")
public class GroupDTO {

    @FieldChecking
    @ApiModelProperty(value = "组名", required = true)
    private String name;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "组链接")
    private String link;

    @ApiModelProperty(value = "关于组")
    private String about;

    @FieldChecking
    @ApiModelProperty(value = "组类型：1服务，2订阅，3群组")
    private String type;

    @FieldChecking
    @ApiModelProperty(value = "是否私有：0公开，1私有")
    private String isPrivate;
}
