package com.leyuna.blog.repository.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
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
    @TableField(value = "update_Dt", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDt;

    /**
     * 创建时间
     */
    @TableField(value = "create_Dt", fill = FieldFill.INSERT)
    private LocalDateTime createDt;

}
