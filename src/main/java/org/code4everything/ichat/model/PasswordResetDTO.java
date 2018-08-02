package org.code4everything.ichat.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pantao
 * @since 2018-08-02
 */
@Data
@ApiModel(value = "password", description = "重置密码")
public class PasswordResetDTO {

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "验证码", required = true)
    private Integer code;

    @ApiModelProperty(value = "新密码", required = true)
    private String password;
}
