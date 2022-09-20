package com.leyuna.blog.control;

import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-28
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件  返回文件id
     *
     * @param files
     * @return
     */
    @PostMapping("/fileUpload")
    public DataResponse<List<String>> uploadFile(List<MultipartFile> files, String type) {
        List<String> fileIds = fileService.uploadFile(files,type);
        return DataResponse.of(fileIds);
    }
}
