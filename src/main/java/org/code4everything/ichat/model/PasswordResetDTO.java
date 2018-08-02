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
@ApiModel(value = "password", description = "重置密码")
public class PasswordResetDTO {

    @FieldChecking
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @FieldChecking(message = "验证码格式不正确", expression = "val!=null&&val>100000&&val<1000009")
    @ApiModelProperty(value = "验证码", required = true)
    private Integer code;

    @FieldChecking(message = "密码长度为6至20位字符", expression = "val!=null&&val.length()>5&&val.length()<21")
    @ApiModelProperty(value = "新密码", required = true)
    private String password;
}
