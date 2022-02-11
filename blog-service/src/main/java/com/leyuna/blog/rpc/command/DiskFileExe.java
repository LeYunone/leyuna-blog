package com.leyuna.blog.rpc.command;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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


    public Double selectAllFileSize(String userId){
        ResponseBean<Double> doubleResponseBean = leyunaDiskRpcService.selectAllFileSize(userId);
        AssertUtil.isTrue(doubleResponseBean.isStatus(), doubleResponseBean.getMessage());
        return doubleResponseBean.getData();
    }

    public ResponseBean<Integer> requestSaveFile(UpFileBean fileBean){
        String userId = fileBean.getUserId();
        MultipartFile multipartFile = fileBean.getFiles().get(0);
        ResponseBean<Integer> integerResponseBean = leyunaDiskRpcService.requestSaveFile(userId, multipartFile);
        AssertUtil.isTrue(integerResponseBean.isStatus(),integerResponseBean.getMessage());
        return integerResponseBean;
    }

    public ResponseBean saveFile(UpFileBean fileBean){
        String userId = fileBean.getUserId();
        MultipartFile multipartFile = fileBean.getFiles().get(0);
        String saveTime = fileBean.getSaveTime();
        ResponseBean responseBean = leyunaDiskRpcService.saveFile(userId, multipartFile, saveTime);
        AssertUtil.isTrue(responseBean.isStatus(),responseBean.getMessage());
        return responseBean;
    }

    public ResponseBean deleteFile(String id){
        return leyunaDiskRpcService.deleteFile(id);
    }

    public ResponseEntity downloadFile(String id){
        UserCO user=(UserCO)StpUtil.getSession().get("user");
        return leyunaDiskRpcService.downloadFile(id,user.getId());
    }

}
