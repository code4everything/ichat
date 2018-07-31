package org.code4everything.ichat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 操作日志
 *
 * @author pantao
 * @since 2018-07-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logger {

    @Id
    private String id;

    /**
     * 操作者
     */
    private String userId;

    /**
     * 操作时间
     */
    private Long time;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 具体描述
     */
    private String description;

    /**
     * 日志级别：INFO,WARNING,ERROR,FATAL
     */
    private String level;
}
