package com.leyuna.blog.model.co;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-07-31
 * 菜单chucan
 */
@Data
public class MenuTreeCO implements Serializable {

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
     * WEB 树形插件用
     */
    private String id;
    /**
     * WEB 树形插件用
     */
    private String label;
    /**
     * WEB 树形插件用
     */
    private String value;
    /**
     * WEB 树形插件用
     */
    private List<MenuTreeCO> children;
}
