package com.leyuna.blog.co.disk;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-04-21
 * 文件校验返回值
 */
@Getter
@Setter
@ToString
public class FileValidatorCO {

    /**
     * 文件唯一标识 MD5
     */
    private String identifier;

    /**
     * 返回编码
     */
    private Integer responseType;

}
