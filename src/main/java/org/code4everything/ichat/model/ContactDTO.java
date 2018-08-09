package org.code4everything.ichat.model;

import com.zhazhapan.util.annotation.FieldChecking;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pantao
 * @since 2018-08-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "contact", description = "更新好友标签和备注名")
public class ContactDTO {

    @FieldChecking
    @ApiModelProperty(value = "列表编号", required = true)
    private String id;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty(value = "备注名")
    private String noteName;
}
