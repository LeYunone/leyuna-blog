package com.leyuna.blog.core.dao.repository.entry;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
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
    @TableField(value = "create_Dt", fill = FieldFill.INSERT)
    private LocalDateTime createDt;

    /**
     * 更新时间
     */
    @TableField(value = "update_Dt", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDt;

}
