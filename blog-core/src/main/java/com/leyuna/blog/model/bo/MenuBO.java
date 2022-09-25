package com.leyuna.blog.model.bo;

import lombok.Data;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-26
 */
@Data
public class MenuBO {

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

    /**
     * 菜单地位
     */
    private Integer menuPosition;
}
