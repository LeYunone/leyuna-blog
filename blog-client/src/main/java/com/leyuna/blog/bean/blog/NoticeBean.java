package com.leyuna.blog.bean.blog;

import lombok.Getter;
import lombok.Setter;

/**
 * @author pengli
 * @create 2021-08-26 16:07
 *
 *  公告对象
 */
@Getter
@Setter
public class NoticeBean {

    private String id;

    private String title;

    private String content;
    //公告类型
    private Integer type;
}
