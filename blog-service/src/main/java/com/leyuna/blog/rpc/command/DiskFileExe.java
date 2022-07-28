package com.leyuna.blog.rpc.command;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.co.disk.FileValidatorCO;
import com.leyuna.blog.co.disk.UserFileInfoCO;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * @author pengli
 * @create 2021-12-24 11:02
 */
@Component
public class DiskFileExe {

    @Autowired
    private LeyunaDiskRpcService leyunaDiskRpcService;

    @Value("${disk.max.memory:1}")
    private Long maxMemory;

    public DataResponse<UserFileInfoCO> selectFile(FileQueryBean queryBean) {
        UserCO user = (UserCO) StpUtil.getSession().get("user");
        queryBean.setUserId(user.getId());
        //查所有文件 0
        if (ObjectUtils.isNotEmpty(queryBean.getFileType()) && queryBean.getFileType() == 0) {
            queryBean.setFileType(null);
        }
        DataResponse<UserFileInfoCO> userFileInfoCODataResponse = leyunaDiskRpcService.selectFile(queryBean);
        resultValidate(userFileInfoCODataResponse);
        UserFileInfoCO data = userFileInfoCODataResponse.getData();
        Page<FileInfoCO> fileinfos = data.getFileinfos();
        List<FileInfoCO> records = fileinfos.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.stream().forEach(co -> {
                Long fileSize = co.getFileSize();
                if(fileSize<1024){
                    co.setFileSizeText(fileSize+"B");
                }else if(fileSize<1048576){
                    co.setFileSizeText(String.format("%.2f", fileSize/1024.0)+"KB");
                }else if(fileSize<1073741824){
                    co.setFileSizeText(String.format("%.2f", fileSize/(1024*1024.0))+"MB");
                }else{
                    co.setFileSizeText(String.format("%.2f", fileSize/(1024*1024*1024.0))+"G");
                }
            });
        }
        double total = (data.getFileTotal() / maxMemory) * 100;

        data.setFileTotalStr(String.format("%.2f", total));
        return DataResponse.of(data);
    }


    public Long selectAllFileSize() {
        UserCO user = (UserCO) StpUtil.getSession().get("user");
        String userId = user.getId();
        DataResponse<Long> doubleDataResponse = leyunaDiskRpcService.selectUserFileSize(userId);
        resultValidate(doubleDataResponse);
        return doubleDataResponse.getData();
    }

    public DataResponse<FileValidatorCO> requestSaveFile(MultipartFile file) {

        AssertUtil.isFalse(ObjectUtils.isEmpty(file), "操作失败：文件接收为空");
        //用户编号
        String userId = (String) StpUtil.getLoginId();

        DataResponse<FileValidatorCO> integerDataResponse = leyunaDiskRpcService.requestSaveFile(userId, file);
        resultValidate(integerDataResponse);
        //0 是服务器内部处理文件    1 是需要在服务器生成文件
        return integerDataResponse;
    }

    public DataResponse deleteFile(String id) {
        UserCO user = (UserCO) StpUtil.getSession().get("user");
        DataResponse responseBean = leyunaDiskRpcService.deleteFile(id, user.getId());
        return responseBean;
    }

    public FileInfoCO downloadFile(String id) {
        UserCO user = (UserCO) StpUtil.getSession().get("user");
        DataResponse<FileInfoCO> fileInfoCODataResponse = leyunaDiskRpcService.downloadFile(id, user.getId());
        resultValidate(fileInfoCODataResponse);
        FileInfoCO data = fileInfoCODataResponse.getData();
        return data;
    }

    public <T> T resultValidate(DataResponse<T> response, Object... obj) {
        if (response.isStatus()) {
            if (Objects.nonNull(response.getData())) {
                return response.getData();
            } else {
                return response.getData();
            }
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
}
