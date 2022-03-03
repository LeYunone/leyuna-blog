package com.leyuna.blog.rpc.command;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.error.SystemAsserts;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
        DataResponse<Page<FileInfoCO>> responseBean = leyunaDiskRpcService.selectFile(queryBean);
        return responseBean.getData();
    }


    public Double selectAllFileSize(String userId){
        DataResponse<Double> doubleDataResponse = leyunaDiskRpcService.selectAllFileSize(userId);
        AssertUtil.isTrue(doubleDataResponse.isStatus(), doubleDataResponse.getMessage());
        return doubleDataResponse.getData();
    }

    public DataResponse<Integer> requestSaveFile(UpFileBean fileBean){
        String userId = fileBean.getUserId();
        MultipartFile multipartFile = fileBean.getFiles().get(0);
        DataResponse<Integer> integerDataResponse = leyunaDiskRpcService.requestSaveFile(userId, multipartFile);
        AssertUtil.isTrue(integerDataResponse.isStatus(),integerDataResponse.getMessage());
        return integerDataResponse;
    }

    public DataResponse saveFile(UpFileBean fileBean){
        String userId = fileBean.getUserId();
        MultipartFile multipartFile = fileBean.getFiles().get(0);
        String saveTime = fileBean.getSaveTime();
        DataResponse responseBean = leyunaDiskRpcService.saveFile(userId, multipartFile, saveTime);
        AssertUtil.isTrue(responseBean.isStatus(),responseBean.getMessage());
        return responseBean;
    }

    public DataResponse deleteFile(String id){
        UserCO user=(UserCO)StpUtil.getSession().get("user");
        DataResponse responseBean = leyunaDiskRpcService.deleteFile(id, user.getId());
        return responseBean;
    }

    public FileInfoCO downloadFile(String id){
        UserCO user=(UserCO)StpUtil.getSession().get("user");
        DataResponse<FileInfoCO> fileInfoCODataResponse = leyunaDiskRpcService.downloadFile(id, user.getId());
        FileInfoCO data = fileInfoCODataResponse.getData();
        AssertUtil.isFalse(null==data, SystemAsserts.REQUEST_FAIL.getMsg());
        return data;
    }

}
