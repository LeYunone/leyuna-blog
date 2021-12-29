package com.leyuna.blog.bean.disk;

import com.leyuna.blog.bean.QueryPage;
import lombok.*;

/**
 * @author pengli
 * @create 2021-12-28 15:27
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileQueryBean extends QueryPage {

    private String id;

    private String name;

    private Integer type;

    private String userId;

}
