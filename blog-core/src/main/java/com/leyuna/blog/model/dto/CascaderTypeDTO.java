package com.leyuna.blog.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-24 17:29
 *
 * 分类导航和分类的级联选择
 */
@Getter
@Setter
public class CascaderTypeDTO {

    /**
     * 值
     */
    private String value;

    /**
     * 展示文本
     */
    private String label;

    private List<CascaderTypeDTO> children;
}
