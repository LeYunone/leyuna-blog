package com.leyuna.blog.service.file;

import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.bean.UpFileBean;
import com.leyuna.blog.rpc.command.DiskFileExe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-12-24 11:03
 */
@Service
public class DiskDomain {

    @Autowired
    private DiskFileExe fileExe;

    public ResponseBean selectFile(String id){
          return fileExe.selectFile(id);
    }

    public ResponseBean requestSaveFile(UpFileBean fileBean){
        return fileExe.requestSaveFile(fileBean);
    }

    public ResponseBean saveFile(UpFileBean fileBean){
        return fileExe.saveFile(fileBean);
    }

    public ResponseBean deleteFile(String id){
        return fileExe.deleteFile(id);
    }
    public ResponseEntity downloadFile(String id){
        return fileExe.downloadFile(id);
    }
}
