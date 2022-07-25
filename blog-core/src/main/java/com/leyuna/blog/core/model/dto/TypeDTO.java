package com.leyuna.blog.core.model.dto;

import com.leyuna.blog.core.model.query.QueryPage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author pengli
 * @create 2022-03-11 14:33
 */
@Getter
@Setter
@ToString
public class TypeDTO extends QueryPage {

    /**
     * 标签id
     */
    private String id;
    /**
     * 标签名
     */
    private String typeName;
    /**
     * 创建时间
     */
    private LocalDateTime createDt;
    /**
     * 使用次数
     */
    private Integer useCount;
    /**
     * 最后使用时间
     */
    private LocalDateTime updateDt;
    /**
     * 分类导航
     */
    private String fatherType;
}
