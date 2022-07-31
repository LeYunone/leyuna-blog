package com.leyuna.blog.dao.repository.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
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
    private String typeNav;

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
