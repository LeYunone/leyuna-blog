package com.leyuna.blog.model.dto;

import lombok.Data;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-27
 */
@Data
public class MenuDTO {

    private Integer menuParentId;

    private String menuName;

    private String menuUrl;

    private Integer menuPosition;
}
