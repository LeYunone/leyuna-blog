package com.blog.daoservice.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * (Blog)实体对象
 *
 * @author pengli
 * @since 2021-08-19 17:57:08
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("blog")
public class Blog {

    private static final long serialVersionUID = -29752677651221342L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "tag")
    private String tag;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    private String remarks;
}
