package com.leyuna.blog.model.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-21
 * 文件返回值
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {

    private String url;

    private String md5Code;

}
