package com.leyuna.blog.core.model.co;

import lombok.*;

import java.io.Serializable;
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
public class LuceneCO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long totole;

    private List listData;
}
