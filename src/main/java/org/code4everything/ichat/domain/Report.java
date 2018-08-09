package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 违规举报
 *
 * @author pantao
 * @since 2018-08-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    private String id;

    /**
     * 举报者
     */
    private String userId;

    /**
     * 被举报者
     */
    private String reportUserId;

    /**
     * 举报原因
     */
    private String reason;

    /**
     * 具体描述
     */
    private String description;

    /**
     * 图片
     */
    private String image;

    /**
     * 举报是否合理（由管理员审核后更新此数据）
     */
    private Boolean isReasonable;

    /**
     * 是否已经处理
     */
    private Boolean isHandled;

    /**
     * 处理结果
     */
    private String result;

    /**
     * 创建时间
     */
    private Long createTime;
}
