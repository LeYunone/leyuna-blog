package com.leyuna.blog.model.dto;

import com.leyuna.blog.constant.enums.UploadFileTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-30 19:33
 *  文件对象
 */
@Getter
@Setter
public class FileDTO {

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件大小
     */
    private Integer size;

    private MultipartFile file;

    private List<MultipartFile> files;

    private UploadFileTypeEnum type;
}
