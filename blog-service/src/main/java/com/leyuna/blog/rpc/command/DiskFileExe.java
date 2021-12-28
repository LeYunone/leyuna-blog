package com.leyuna.blog.rpc.command;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.disk.FileInfoCO;
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

    public Page<FileInfoCO> selectFile(FileQueryBean queryBean){
        ResponseBean responseBean = leyunaDiskRpcService.selectFile(queryBean);
        return (Page<FileInfoCO>) responseBean.getData();
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
