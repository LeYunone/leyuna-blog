package com.leyuna.blog.bean;

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

    Integer index = 1;

    Integer size = 10;

    private String orderCondition;

    private String conditionName;
}
