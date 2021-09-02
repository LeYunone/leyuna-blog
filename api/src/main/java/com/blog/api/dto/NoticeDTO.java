package com.blog.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author pengli
 * @create 2021-08-26 16:09
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDTO implements Serializable {

    private String title;

    private String content;

    private Integer type;
}
