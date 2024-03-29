package com.leyuna.blog.model.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author pengli
 * @create 2021-12-09 16:19
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QueryPage implements Serializable {

    protected Integer index = 1;

    protected Integer size = 10;

    /**
     * 是否开启分页
     */
    protected boolean isPage;

    private String orderCondition;
}
