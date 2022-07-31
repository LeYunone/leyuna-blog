package com.leyuna.blog.model.co;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-07-31
 * 菜单chucan
 */
@Data
public class MenuCO implements Serializable {

    /**
     * 菜单id
     */
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
}
