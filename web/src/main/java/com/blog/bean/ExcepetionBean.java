package com.blog.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author pengli
 * @create 2021-08-31 15:54
 *
 * 全局异常对象
 */
@Getter
@Setter
public class ExcepetionBean {

    /**
     * 404报错
     */
    private Integer code;

    private String message;
}
