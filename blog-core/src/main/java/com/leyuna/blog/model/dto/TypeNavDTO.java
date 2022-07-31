package com.leyuna.blog.core.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author pengli
 * @create 2022-03-11 15:19
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TypeNavDTO {

    /**
     * 分类导航标签id
     */
    private String id;

    /**
     * 分类导航名称
     */
    private String typeNavName;
}
