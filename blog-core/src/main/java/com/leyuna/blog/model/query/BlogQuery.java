package com.leyuna.blog.model.query;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-01
 */
@Data
public class BlogQuery extends QueryPage{

    /**
     * 顶级菜单id
     */
    private Integer menuId;

    private String createDt;

    private LocalDateTime createDate;

    private List<Integer> menuTopIds;
}
