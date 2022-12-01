package com.leyuna.blog.model.dto;

import com.leyuna.blog.model.query.QueryPage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
public class BlogDTO extends QueryPage {
    /**
     * 内容  2021/8/25 版本  暂时只想存储markdown形式原文本
     */
    private String blogContent;
    /**
     * 标签 S 数组形式
     */
    private String [] tags;

    private String title;

    private String foreword;

    /**
     * 博客类型  1 博客  2 公告   3 个人   4 评测
     */
    private Integer blogType;

    /**
     * 多个博客类型  用于查询
     */
    private List<Integer> blogTypes;

    private Integer menuId;

    /**
     * 标签  逗号分隔形式
     */
    private String tag;

    /**
     * 封面
     */
    private String blogCover;

    private String id;

    private String blogLink;

    /**
     * 封面文件
     */
    private MultipartFile cover;
    /**
     * 自定义字段（1、刷题日记为难度）
     */
    private String customField;
}
