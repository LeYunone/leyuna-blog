package com.leyuna.blog.bean.blog;

import com.leyuna.blog.bean.QueryPage;
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
public class TagBean extends QueryPage {

    private String id;

    private String tagName;

    private LocalDateTime createDt;

    private Integer useCount;

    private LocalDateTime updateDt;
}
