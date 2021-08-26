package com.blog.api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * (WebHistory)出参
 *
 * @author pengli
 * @since 2021-08-26 15:41:47
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebHistoryDTO {


    private Integer id;

    private String title;

    private String content;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

