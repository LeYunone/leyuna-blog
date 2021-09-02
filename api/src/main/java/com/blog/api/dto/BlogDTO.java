package com.blog.api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
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
public class BlogDTO implements Serializable {


    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 发布时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd")
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

    //存储的是用逗号分隔的标签名
    private String tag;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String remarks;
}

