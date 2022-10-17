package com.leyuna.blog.control;

import com.leyuna.blog.model.co.FileCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.model.constant.FileResponse;
import com.leyuna.blog.model.query.FileQuery;
import com.leyuna.blog.service.FileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PostMapping("/uploads")
    public DataResponse<FileResponse> uploadFile(List<MultipartFile> files,@Param("type") Integer type) {
        FileResponse fileResponse = fileService.uploadFiles(files, type);
        return DataResponse.of(fileResponse);
    }

    @PostMapping("/upload")
    public DataResponse<FileResponse> uploadFile(MultipartFile file, @Param("type") Integer type) {
        FileResponse fileResponse = fileService.uploadFile(file, type);
        return DataResponse.of(fileResponse);
    }

    @GetMapping("/list")
    public DataResponse<List<FileCO>> queryFile(FileQuery fileQuery){
        List<FileCO> files = fileService.getFiles(fileQuery);
        return DataResponse.of(files);
    }

}
