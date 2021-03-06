package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 文件
 *
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
     * 文件大小
     */
    private Long size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 可访问路径
     */
    private String url;

    /**
     * 创建时间
     */
    private Long createTime;
}
