package com.leyuna.blog.co.blog;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BlogDO)出参
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
public class BlogCO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String blogCover;

    /**
     * 发布时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDateTime createDt;


    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 内容
     */
    private String blogContent;

    private String type;

    //存储的是用逗号分隔的标签名
    private String tag;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDt;

    private String remarks;
    
    private String blogLink;
}

