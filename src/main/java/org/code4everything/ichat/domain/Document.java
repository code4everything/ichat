package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author pantao
 * @since 2018-08-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    private String id;

    /**
     * 本地路径
     */
    private String local;

    /**
     * 可访问路径
     */
    private String url;

    /**
     * 创建时间
     */
    private Long createTime;
}
