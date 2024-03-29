package com.leyuna.blog.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-23 13:27
 *
 * 树形分类 对应前端树形结构
 */
@Getter
@Setter
public class TreeTypeDTO {

    /**
     * 节点id
     */
    private String id;
    /**
     *  节点名
     */
    private String  label;
    /**
     * 子节点
     */
    private List<TreeTypeDTO> children;
}
