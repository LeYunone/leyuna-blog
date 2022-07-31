package com.leyuna.blog.model.dto;

import com.leyuna.blog.model.query.QueryPage;
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
public class TagDTO extends QueryPage {

    private String id;

    private String tagName;

    private LocalDateTime createDt;

    private Integer useCount;

    private LocalDateTime updateDt;
}
