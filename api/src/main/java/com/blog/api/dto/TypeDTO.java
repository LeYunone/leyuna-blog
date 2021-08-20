package com.blog.api.dto;

import lombok.*;

import java.time.LocalDateTime;

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
public class TypeDTO {

    private Integer id;

    private String typeName;

    private LocalDateTime createTime;

    private Integer useCount;

    private LocalDateTime lastUserTime;

    private String userStatus;

}
