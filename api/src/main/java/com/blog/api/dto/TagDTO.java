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
public class TagDTO {

    /**
     * 标签id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 使用次数
     */
    private Integer useCount;

    private LocalDateTime lastUserTime;

    private String userStatus;
}
