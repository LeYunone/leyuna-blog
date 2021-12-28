package com.leyuna.blog.rpc.command;

import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.bean.UpFileBean;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author pengli
 * @create 2021-12-24 11:02
 */
@Component
public class DiskFileExe {

    @Autowired
    private LeyunaDiskRpcService leyunaDiskRpcService;

    public ResponseBean selectFile(String id){
        return leyunaDiskRpcService.selectFile(id);
    }

    public ResponseBean requestSaveFile(UpFileBean fileBean){
        return leyunaDiskRpcService.requestSaveFile(fileBean);
    }

    public ResponseBean saveFile(UpFileBean fileBean){
        return leyunaDiskRpcService.saveFile(fileBean);
    }

    public ResponseBean deleteFile(String id){
        return leyunaDiskRpcService.deleteFile(id);
    }

    public ResponseEntity downloadFile(String id){
        return leyunaDiskRpcService.downloadFile(id);
    }

}
