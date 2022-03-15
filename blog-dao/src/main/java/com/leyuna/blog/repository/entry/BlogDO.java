package com.leyuna.blog.repository.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BlogDO)表实体类
 *
 * @author pengli
 * @since 2022-03-15 17:04:22
 */
@Getter
@Setter
@TableName("blog")
public class BlogDO implements Serializable {
    private static final long serialVersionUID = 566700766741701593L;
    /**
     * 博客编号
     */
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 内容
     */
    private String blogContent;

    /**
     * 分类
     */
    private String type;

    /**
     * 标签
     */
    private String tag;

    /**
     * 备注前言
     */
    private String remarks;

    /**
     * 博客类型
     */
    private Integer blogType;

    /**
     * 更新时间
     */
    @TableField(value = "update_Dt", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDt;

    /**
     * 发布时间
     */
    @TableField(value = "create_Dt", fill = FieldFill.INSERT)
    private LocalDateTime createDt;

}
