package com.leyuna.blog.co.disk;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    private String createDt;

    private String updateDt;

    private Integer deleted;

    /**
     * 文件大小 单位为B
     */
    private Long fileSize;

    /**
     * 通过单位转化 翻译出来的文件大小  1KB = 1024B
     */
    private String fileSizeText;

    private String userId;

    private String fileTypeText;

    /**
     * 文件类型：1图片、2音视、3文档、4其他文件
     */
    private Integer fileType;

    private String saveDt;

    private byte[] base64File;
}
