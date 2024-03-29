package com.leyuna.blog.model.co;

import lombok.Data;

import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-26
 */
@Data
public class MenuCO {
    /**
     * 菜单id
     */
    private String menuId;

    /**
     * 上层id
     */
    private String menuParentId;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 菜单路径
     */
    private String menuUrl;

    /**
     * 子菜单
     */
    private List<MenuTreeCO> childrenMenu;
}
