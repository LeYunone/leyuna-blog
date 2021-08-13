package com.blog.daoservice.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.*;

import java.time.LocalDateTime;

/**
 * (Blog)实体对象
 *
 * @author pengli
 * @since 2021-08-13 15:56:44
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

    private static final long serialVersionUID = -70312171175383184L;

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
    @TableField(value = "createTime")
    private LocalDateTime createTime;

    /**
     * 点击量
     */
    @TableField(value = "clickCount")
    private Integer clickCount;

    /**
     * 评论数
     */
    @TableField(value = "commentCount")
    private Integer commentCount;

    /**
     * 内容
     */
    @TableField(value = "blogContent")
    private String blogContent;

}

