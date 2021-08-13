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
     * 图片
     */
    private String base64Str;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 标签
     */
    private Integer [] tags;

    /**
     * 图片 二进制
     */
    private byte[] base64Bytes;

    private String title;
}
