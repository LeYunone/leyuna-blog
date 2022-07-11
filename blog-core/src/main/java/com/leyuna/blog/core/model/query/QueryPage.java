package com.leyuna.blog.core.model.query;

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

    private String orderCondition;
}
