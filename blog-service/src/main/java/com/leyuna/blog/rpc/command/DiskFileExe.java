package com.leyuna.blog.rpc.command;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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

    public ResponseBean<Integer> requestSaveFile(UpFileBean fileBean){
        String userId = fileBean.getUserId();
        MultipartFile multipartFile = fileBean.getFiles().get(0);
        LocalDateTime saveTime = fileBean.getSaveTime();
        ResponseBean<Integer> integerResponseBean = leyunaDiskRpcService.requestSaveFile(userId, multipartFile, saveTime);
        AssertUtil.isTrue(integerResponseBean.isStatus(),integerResponseBean.getMessage());
        return integerResponseBean;
    }

    public ResponseBean saveFile(UpFileBean fileBean){
        String userId = fileBean.getUserId();
        MultipartFile multipartFile = fileBean.getFiles().get(0);
        LocalDateTime saveTime = fileBean.getSaveTime();
        ResponseBean responseBean = leyunaDiskRpcService.saveFile(userId, multipartFile, saveTime);
        AssertUtil.isTrue(responseBean.isStatus(),responseBean.getMessage());
        return responseBean;
    }

    public ResponseBean deleteFile(String id){
        return leyunaDiskRpcService.deleteFile(id);
    }

    public ResponseEntity downloadFile(String id){
        return leyunaDiskRpcService.downloadFile(id);
    }

}
