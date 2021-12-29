package com.leyuna.blog.co.disk;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author pengli
 * @create 2021-12-28 14:34
 * 文件类型出参
 */
@Getter
@Setter
@ToString
public class FileInfoCO {

    private String id;

    private String name;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private Integer deleted;

    private Long fileSize;

    private String userId;

    private Long fileSizeTotal;

    /**
     * 文件类型：1图片、2音视、3文档、4其他文件
     */
    private Integer fileType;
}