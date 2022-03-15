package com.leyuna.blog.repository.entry;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Getter;
import lombok.Setter;

/**
 * (Comment)表实体类
 *
 * @author pengli
 * @since 2022-03-15 17:04:23
 */
@Getter
@Setter
@TableName("comment")
public class CommentDO implements Serializable {
    private static final long serialVersionUID = -23318074016832273L;
    private String id;

    /**
     * 评论内容
     */
    private String content;

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
