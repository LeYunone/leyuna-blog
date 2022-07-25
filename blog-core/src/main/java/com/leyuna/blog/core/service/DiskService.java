package com.leyuna.blog.core.service;

import com.leyuna.blog.core.model.constant.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pengli
 * @create 2021-12-24 11:03
 */
@Service
public class DiskService {

    @Autowired
    private DiskFileExe fileExe;

    public DataResponse selectFile (FileQueryBean queryBean) {
        return fileExe.selectFile(queryBean);
    }

    public DataResponse requestSaveFile (MultipartFile file) {
        return fileExe.requestSaveFile(file);
    }

    public DataResponse deleteFile (String id) {
        //删除文件
        fileExe.deleteFile(id);
        //删除之后获得最新的总内存大小
        return DataResponse.of(fileExe.selectAllFileSize());
    }

    public FileInfoCO downloadFile (String id) {
        return fileExe.downloadFile(id);
    }
}
