package com.leyuna.blog.dao.repository.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-07-31
 */
@Getter
@Setter
@TableName("menu")
public class MenuDO {

    /**
     * 菜单id
     */
    @TableId(value = "menu_id",type = IdType.AUTO)
    private Integer menuId;

    /**
     * 上层id
     */
    private Integer menuParentId;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 菜单路径
     */
    private String menuUrl;

    /**
     * 菜单权限
     */
    private String menuPermison;

    @TableField(value = "create_Dt", fill = FieldFill.INSERT)
    private LocalDateTime createDt;

    @TableField(value = "update_Dt", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDt;
}
