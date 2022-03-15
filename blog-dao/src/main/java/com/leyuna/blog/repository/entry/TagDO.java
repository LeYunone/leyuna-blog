package com.leyuna.blog.repository.entry;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Getter;
import lombok.Setter;

/**
 * (Tag)表实体类
 *
 * @author pengli
 * @since 2022-03-15 17:04:23
 */
@Getter
@Setter
@TableName("tag")
public class TagDO implements Serializable {
    private static final long serialVersionUID = -42854597973732399L;
    private String id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 标签使用次数
     */
    private Integer useCount;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

}
