package com.leyuna.blog.bean.disk;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * @author pengli
 * @create 2021-12-24 16:51
 * 上传文件
 */
@Getter
@Setter
@ToString
public class UpFileBean {

    /**
     * 上传文件人的userId
     */
    private String userId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 多文件
     */
    private MultipartFile[] files;

    /**
     * 保存时间
     */
    private LocalDateTime saveTime;
}
