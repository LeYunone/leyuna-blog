package com.leyuna.blog.model.co;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Type)出参
 *
 * @author pengli
 * @since 2021-08-23 13:54:42
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeCO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDateTime createDt;

    /**
     * 使用次数
     */
    private Integer useCount;

    /**
     * 最后使用时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDateTime updateDt;

    /**
     * 分类导航
     */
    private String typeNav;

    private String userStatus;
}

