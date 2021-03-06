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
@ApiModel(value = "PasswordUpdate", description = "更新密码")
public class PasswordUpdateDTO {

    @ApiModelProperty(value = "原密码", required = true)
    @FieldChecking
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @FieldChecking(message = "password length must between 6 and 20", expression = "val.length()>5&&val.length()<21")
    private String newPassword;
}
