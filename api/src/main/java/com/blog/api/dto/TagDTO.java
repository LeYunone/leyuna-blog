package com.blog.api.dto;

import lombok.*;

/**
 * @author pengli
 * @create 2021-08-12 13:25
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDTO {

    private Integer id;

    private String tagName;
}
