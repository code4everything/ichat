package org.code4everything.ichat.model;

import com.zhazhapan.util.annotation.FieldChecking;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pantao
 * @since 2018/8/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "report", description = "新增投诉反馈")
public class ReportDTO {

    @FieldChecking
    @ApiModelProperty(value = "被举报者编号", required = true)
    private String reportUserId;

    @FieldChecking
    @ApiModelProperty(value = "预报理由", required = true)
    private String reason;

    @ApiModelProperty(value = "详细描述")
    private String description;

    @ApiModelProperty(value = "图片URL")
    private String image;
}
