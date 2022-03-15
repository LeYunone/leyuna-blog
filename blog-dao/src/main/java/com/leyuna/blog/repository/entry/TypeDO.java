package com.leyuna.blog.repository.entry;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Getter;
import lombok.Setter;

/**
 * (Type)表实体类
 *
 * @author pengli
 * @since 2022-03-15 17:04:24
 */
@Getter
@Setter
@TableName("type")
public class TypeDO implements Serializable {
    private static final long serialVersionUID = 729641133195507081L;
    /**
     * 标签id
     */
    private String id;

    /**
     * 标签名
     */
    private String typeName;

    /**
     * 使用次数
     */
    private Integer useCount;

    /**
     * 分类导航
     */
    private String fatherType;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

}
