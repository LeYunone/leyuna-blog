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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author pengli
 * @create 2021-12-24 11:02
 */
@Component
public class DiskFileExe {

    @Autowired
    private LeyunaDiskRpcService leyunaDiskRpcService;

    public Page<FileInfoCO> selectFile(FileQueryBean queryBean){
        ResponseBean<Page<FileInfoCO>> responseBean = leyunaDiskRpcService.selectFile(queryBean);
        return responseBean.getData();
    }

    public ResponseBean<List<MultipartFile>> requestSaveFile(UpFileBean fileBean){
        return leyunaDiskRpcService.requestSaveFile(fileBean.getUserId(),fileBean.getFiles().get(0),fileBean.getSaveTime());
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
