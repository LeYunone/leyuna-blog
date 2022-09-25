package com.leyuna.blog.service;

import com.leyuna.blog.baseinterface.FileInterface;
import com.leyuna.blog.constant.enums.UploadFileTypeEnum;
import com.leyuna.blog.model.constant.FileResponse;
import com.leyuna.blog.model.dto.FileDTO;
import com.leyuna.blog.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-28
 */
@Service
public class FileService {

    @Autowired
    private HttpServletRequest request;

    public FileResponse uploadFiles(List<MultipartFile> files, String type) {

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFiles(files);
        fileDTO.setType(UploadFileTypeEnum.getEnum(type));

        FileInterface fileService = SpringContextUtil.getBean(fileDTO.getType().getFileService());
        return fileService.uploadFile(fileDTO);
    }

    public FileResponse uploadFile(MultipartFile file, String type) {

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFile(file);
        fileDTO.setType(UploadFileTypeEnum.getEnum(type));

        FileInterface fileService = SpringContextUtil.getBean(fileDTO.getType().getFileService());
        return fileService.uploadFile(fileDTO);
    }
}
