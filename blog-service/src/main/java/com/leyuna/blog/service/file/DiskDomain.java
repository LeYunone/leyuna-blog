package com.leyuna.blog.service.file;

import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.rpc.command.DiskFileExe;
import org.springframework.beans.factory.annotation.Autowired;
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
}
