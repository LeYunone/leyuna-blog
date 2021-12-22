package com.leyuna.blog.dto;

import lombok.*;

import java.util.List;

/**
 * @author pengli
 * @create 2021-09-10 13:51
 *
 * 记录lucene查询结果
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LuceneDTO {

    private long totole;

    private List listData;
}
