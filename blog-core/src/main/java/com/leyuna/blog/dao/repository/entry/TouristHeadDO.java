package com.leyuna.blog.dao.repository.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (TouristHead)表实体类
 *
 * @author pengli
 * @since 2022-03-15 17:04:24
 */
@Getter
@Setter
@TableName("tourist_head")
public class TouristHeadDO implements Serializable {
    private static final long serialVersionUID = 851632183581476705L;
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 游客ip地址
     */
    private String ip;

    /**
     * 游客头像
     */
    private String head;

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
