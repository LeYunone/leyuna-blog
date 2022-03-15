package com.leyuna.blog.repository.entry;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Getter;
import lombok.Setter;

/**
 * (TypeNav)表实体类
 *
 * @author pengli
 * @since 2022-03-15 17:04:24
 */
@Getter
@Setter
@TableName("type_nav")
public class TypeNavDO implements Serializable {
    private static final long serialVersionUID = -93377623947257249L;
    private String id;

    /**
     * 导航名
     */
    private String typeNavName;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

    /**
     * 创建时间
     */
    private String createDt;

}
