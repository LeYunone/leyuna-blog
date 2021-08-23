package com.blog.api.dto;


import lombok.*;

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
public class TypeNavDTO {


    private Integer id;

    /**
     * 导航名
     */
    private String typeNavName;
}

