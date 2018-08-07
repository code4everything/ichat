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
@ApiModel(value = "Login", description = "登录")
public class LoginDTO {

    @FieldChecking
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @FieldChecking(message = "oldPassword length must between 6 and 20", expression = "val.length()>5&&val.length()<21")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
