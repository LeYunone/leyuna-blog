package com.leyuna.blog.dao.repository.entry;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 导航名
     */
    private String typeNavName;

    /**
     * 更新时间
     */
    @TableField(value = "update_Dt", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDt;

    /**
     * 创建时间
     */
    @TableField(value = "create_Dt", fill = FieldFill.INSERT)
    private LocalDateTime createDt;

}
