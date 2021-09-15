package com.blog.daoservice.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

/**
 * (Comment)实体对象
 *
 * @author
 * @since 2021-09-15 14:47:16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = -50170116670319906L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 评论时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 父评论编号
     */
    @TableField(value = "father_comment_id")
    private Integer fatherCommentId;

    /**
     * 头像
     */
    @TableField(value = "comment_head")
    private String commentHead;

    /**
     * 联系方式
     */
    @TableField(value = "information")
    private String information;

    /**
     * 联系人
     */
    @TableField(value = "name")
    private String name;

    /**
     * 点赞数
     */
    @TableField(value = "goods")
    private Integer goods;

    /**
     * 评论人地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 博客编号
     */
    @TableField(value = "blog_id")
    private Integer blogId;

    /**
     * 被回复人名
     */
    @TableField(value = "respondent")
    private String respondent;

}

