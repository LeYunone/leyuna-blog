package com.leyuna.blog.model.dto;

import com.leyuna.blog.model.query.QueryPage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author pengli
 * @create 2021-09-15 09:06
 */
@Getter
@Setter
@ToString
public class CommentDTO extends QueryPage {

    /**
     * 排序类型 1 时间  2 时间与点赞数
     */
    private Integer sortType;

    private String id;

    /**
     * 评论内容
     */
    private String content;

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
     * 管理员标识
     */
    private String admin;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

}
