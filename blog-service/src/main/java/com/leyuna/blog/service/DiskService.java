package com.leyuna.blog.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.co.disk.DiskCO;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.constant.enums.UserErrorEnum;
import com.leyuna.blog.rpc.command.DiskFileExe;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author pengli
 * @create 2021-12-24 11:03
 */
@Service
public class DiskService {

    @Autowired
    private DiskFileExe fileExe;

    @Value("${disk.max.memory:1}")
    private Long maxMemory;

    public DataResponse selectFile (FileQueryBean queryBean) {
        return fileExe.selectFile(queryBean);
    }

    public DataResponse requestSaveFile (List<MultipartFile> file) {
        AssertUtil.isFalse(CollectionUtils.isEmpty(file), UserErrorEnum.UPLOAD_NOT_FILE.getMsg());
        //用户编号
        String userId = (String) StpUtil.getLoginId();
        UpFileBean fileBean = new UpFileBean();
        fileBean.setFiles(file);
        fileBean.setUserId(userId);
        //得到过滤成功后的文件
        DataResponse<Integer> listDataResponse = fileExe.requestSaveFile(fileBean);
        Integer data = listDataResponse.getData();
        //0 是服务器内部处理文件    1 是需要在服务器生成文件
        return DataResponse.of(data);
    }

    public DataResponse saveFile (UpFileBean fileBean) {
        return fileExe.saveFile(fileBean);
    }

    public DataResponse deleteFile (String id) {
        DataResponse responseBean = fileExe.deleteFile(id);
        UserCO user=(UserCO)StpUtil.getSession().get("user");
        String userId=user.getId();
        //删除之后获得最新的总内存大小
        Double totalSize = fileExe.selectAllFileSize(userId);
        //百分比
        double total = (totalSize / maxMemory) * 100;
        responseBean.setData(String.format("%.2f", total));
        return responseBean;
    }

    public FileInfoCO downloadFile (String id) {
        return fileExe.downloadFile(id);
    }

    public DataResponse getFileList (Integer fileType) {
        return getUserFileInfo(fileType);
    }

    /**
     * 用户上传文件
     *
     * @param file
     */
    public DataResponse uploadFile (List<MultipartFile> file, String saveTime) {
        AssertUtil.isFalse(CollectionUtils.isEmpty(file), UserErrorEnum.UPLOAD_NOT_FILE.getMsg());
        //用户编号
        String userId = (String) StpUtil.getLoginId();
        UpFileBean fileBean = new UpFileBean();
        fileBean.setSaveTime(saveTime);
        fileBean.setFiles(file);
        fileBean.setUserId(userId);
        //上传文件流程
        DataResponse responseBean = fileExe.saveFile(fileBean);

        return getUserFileInfo(0);
    }

    /**
     * 获取用户的文件列表相关信息
     * @param fileType
     * @return
     */
    private DataResponse<DiskCO> getUserFileInfo(Integer fileType){
        DiskCO diskCO = new DiskCO();
        String userId=String.valueOf(StpUtil.getLoginId());
        //用户当前文件列表
        FileQueryBean build = FileQueryBean.builder().userId(userId).type(3).build();
        if (fileType != 0) {
            build.setFileType(fileType);
        }
        Page<FileInfoCO> fileInfoCOPage = fileExe.selectFile(build).getData();
        AssertUtil.isFalse(ObjectUtil.isNull(fileInfoCOPage), SystemErrorEnum.REQUEST_FAIL.getMsg());
        //初始化页面时，默认取前十条展示到所有文件
        List<FileInfoCO> fileInfos = fileInfoCOPage.getRecords();
        //获取内存占有量
        Double totalSize = fileExe.selectAllFileSize(userId);
        //百分比
        double total = (totalSize / maxMemory) * 100;

        //封装基本信息
        diskCO.setFileList(fileInfos);
        diskCO.setFileTotalSize(String.format("%.2f", total));
        diskCO.setFileCount(fileInfoCOPage.getTotal());
        return DataResponse.of(diskCO);
    }
}
