package com.leyuna.blog.bean.blog;

import com.leyuna.blog.bean.QueryPage;
import lombok.*;

/**
 * @author pengli
 * @date 2021-08-10
 *
 * 博客在web层的传输对象
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogBean extends QueryPage {
    /**
     * 内容  2021/8/25 版本  暂时只想存储markdown形式原文本
     */
    private String blogContent;
    /**
     * 类型 S
     */
    private String  type;
    /**
     * 标签 S 数组形式
     */
    private String [] tags;

    private String title;

    private String remarks;

    /**
     * 标签  逗号分隔形式
     */
    private String tag;

    private String id;
}
