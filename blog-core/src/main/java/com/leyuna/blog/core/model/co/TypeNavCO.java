package com.leyuna.blog.core.model.co;


import lombok.*;

import java.io.Serializable;

/**
 * (TypeNav)出参
 *
 * @author pengli
 * @since 2021-08-23 13:34:49
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeNavCO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 导航名
     */
    private String typeNavName;
}

