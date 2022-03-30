package com.leyuna.blog.co.blog;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * (Comment)出参
 *
 * @author
 * @since 2021-09-15 14:47:22
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDateTime createDt;

    /**
     * 父评论编号
     */
    private String fatherCommentId;

    /**
     * 头像
     */
    private String commentHead;

    /**
     * 联系方式
     */
    private String information;

    /**
     * 联系人
     */
    private String name;

    /**
     * 点赞数
     */
    private Integer goods;

    /**
     * 评论人地址
     */
    private String ip;

    /**
     * 博客编号
     */
    private String blogId;

    /**
     * 被回复人名
     */
    private String respondent;

    /**
     * 子评论
     */
    private List<CommentCO> subComment;

    /**
     * 判断是否是站主
     */
    private String admin ;
}

