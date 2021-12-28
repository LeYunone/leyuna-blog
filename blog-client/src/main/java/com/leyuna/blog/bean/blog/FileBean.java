package com.leyuna.blog.bean.blog;

import lombok.Getter;
import lombok.Setter;

/**
 * @author pengli
 * @create 2021-08-30 19:33
 *  文件对象
 */
@Getter
@Setter
public class FileBean {

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件大小
     */
    private Integer size;
}
