package com.leyuna.blog.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Blog)实体对象
 *
 * @author pengli
 * @since 2021-08-31 17:08:21
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 399757404066739698L;

    /**
     * 博客编号
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 发布时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 点击量
     */
    @TableField(value = "click_count")
    private Integer clickCount;

    /**
     * 评论数
     */
    @TableField(value = "comment_count")
    private Integer commentCount;

    /**
     * 内容
     */
    @TableField(value = "blog_content")
    private String blogContent;

    /**
     * 分类
     */
    @TableField(value = "type")
    private String type;

    /**
     * 标签
     */
    @TableField(value = "tag")
    private String tag;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 备注前言
     */
    @TableField(value = "remarks")
    private String remarks;

}

