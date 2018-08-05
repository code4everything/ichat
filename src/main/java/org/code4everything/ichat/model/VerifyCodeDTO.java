package org.code4everything.ichat.model;

import com.zhazhapan.util.annotation.FieldChecking;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pantao
 * @since 2018-08-02
 */
@Data
@ApiModel(value = "Code", description = "发送验证码")
public class VerifyCodeDTO {

    @FieldChecking
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @FieldChecking(message = "method is incorrect", expression = "val!=null&&val>0&&val<9")
    @ApiModelProperty(value = "发送方式：1注册，2重置密码，3修改邮箱", required = true)
    private Integer method;
}
