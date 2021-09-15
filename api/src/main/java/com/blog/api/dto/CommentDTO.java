package com.blog.api.dto;


import lombok.*;

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
public class CommentDTO {


    private Integer id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private LocalDateTime createTime;

    /**
     * 父评论编号
     */
    private Integer fatherCommentId;

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
    private Integer blogId;

    /**
     * 被回复人名
     */
    private String respondent;

    /**
     * 子评论
     */
    private List<CommentDTO> subComment;
}

