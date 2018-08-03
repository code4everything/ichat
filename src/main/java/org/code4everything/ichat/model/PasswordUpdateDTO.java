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
@ApiModel("更新密码")
public class PasswordUpdateDTO {

    @ApiModelProperty(value = "原密码", required = true)
    @FieldChecking
    private String password;

    @ApiModelProperty(value = "新密码", required = true)
    @FieldChecking(message = "密码长度为6至20位字符", expression = "val!=null&&val.length()>5&&val.length()<21")
    private String newPassword;
}
