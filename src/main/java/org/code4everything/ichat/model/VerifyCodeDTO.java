package org.code4everything.ichat.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pantao
 * @since 2018-08-02
 */
@Data
@ApiModel(value = "code", description = "发送验证码")
public class VerifyCodeDTO {

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "发送方式：1注册，2重置密码，3修改邮箱", required = true)
    private Integer method;
}
