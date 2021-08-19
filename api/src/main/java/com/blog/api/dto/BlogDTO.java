package com.blog.api.dto;


import lombok.*;

import java.time.LocalDateTime;

/**
 * (Blog)出参
 *
 * @author pengli
 * @since 2021-08-13 15:56:59
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDTO {


    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;

    /**
     * 点击量
     */
    private Integer clickCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 内容
     */
    private String blogContent;

    private Integer type;

    private Integer tag;

}

