package com.blog.web.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-10 15:05
 *   一般响应结果集
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ResponseBean {

    private String srcData;

    private Object objData;

    private Object[] objDatas;

    private List<Object> listData;
}
