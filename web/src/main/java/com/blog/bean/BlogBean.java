package com.blog.bean;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author pengli
 * @date 2021-08-10
 *
 * 博客在web层的传输对象
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogBean {
    /**
     * 内容
     */
    private String blogContent;
    /**
     * 类型 S
     */
    private Integer  type;
    /**
     * 标签 S 数组形式
     */
    private String [] tags;

    private String title;

    /**
     * 标签  逗号分隔形式
     */
    private String tag;
}
