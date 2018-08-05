package org.code4everything.ichat.model;

import com.zhazhapan.util.annotation.FieldChecking;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pantao
 * @since 2018-08-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserInfo", description = "更新用户基本信息")
public class BasicUserDTO {

    @FieldChecking
    @ApiModelProperty(value = "新用户名", required = true)
    private String username;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("出生日期")
    private Long birth;

    @ApiModelProperty("个人简介")
    private String bio;
}
