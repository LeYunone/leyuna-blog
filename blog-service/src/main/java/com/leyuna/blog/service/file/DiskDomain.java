package com.leyuna.blog.service.file;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.disk.DiskCO;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.error.SystemAsserts;
import com.leyuna.blog.error.UserAsserts;
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
public class DiskDomain {

    @Autowired
    private DiskFileExe fileExe;

    @Value("${disk.max.memory:1}")
    private Long maxMemory;

    public Page<FileInfoCO> selectFile (FileQueryBean queryBean) {
        //查所有文件 0
        if(queryBean.getFileType()==0){
            queryBean.setFileType(null);
        }
        return fileExe.selectFile(queryBean);
    }

    public ResponseBean requestSaveFile (List<MultipartFile> file) {
        AssertUtil.isFalse(CollectionUtils.isEmpty(file), UserAsserts.UPLOAD_NOT_FILE.getMsg());
        //用户编号
        String userId = (String) StpUtil.getLoginId();
        UpFileBean fileBean = new UpFileBean();
        fileBean.setFiles(file);
        fileBean.setUserId(userId);
        //得到过滤成功后的文件
        ResponseBean<Integer> listResponseBean = fileExe.requestSaveFile(fileBean);
        Integer data = listResponseBean.getData();
        //0 是服务器内部处理文件    1 是需要在服务器生成文件
        return ResponseBean.of(data);
    }

    public ResponseBean saveFile (UpFileBean fileBean) {
        return fileExe.saveFile(fileBean);
    }

    public ResponseBean deleteFile (String id) {
        return fileExe.deleteFile(id);
    }

    public FileInfoCO downloadFile (String id) {
        return fileExe.downloadFile(id);
    }

    public DiskCO getFileList (String userId, Integer fileType) {
        DiskCO diskCO = new DiskCO();
        //用户当前文件列表
        FileQueryBean build = FileQueryBean.builder().userId(userId).type(3).build();
        if (fileType != 0) {
            build.setFileType(fileType);
        }
        Page<FileInfoCO> fileInfoCOPage = fileExe.selectFile(build);
        AssertUtil.isFalse(ObjectUtil.isNull(fileInfoCOPage), SystemAsserts.REQUEST_FAIL.getMsg());
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
        return diskCO;
    }

    /**
     * 用户上传文件
     *
     * @param file
     */
    public ResponseBean uploadFile (List<MultipartFile> file, String saveTime) {
        AssertUtil.isFalse(CollectionUtils.isEmpty(file), UserAsserts.UPLOAD_NOT_FILE.getMsg());
        //用户编号
        String userId = (String) StpUtil.getLoginId();
        UpFileBean fileBean = new UpFileBean();
        fileBean.setSaveTime(saveTime);
        fileBean.setFiles(file);
        fileBean.setUserId(userId);
        //上传文件流程
        ResponseBean responseBean = fileExe.saveFile(fileBean);

        DiskCO diskCO = new DiskCO();
        //获取内存占有量
        Double totalSize = fileExe.selectAllFileSize(userId);
        //百分比
        double total = (totalSize / maxMemory) * 100;
        diskCO.setFileTotalSize(String.format("%.2f", total));
        return ResponseBean.of(diskCO);
    }
}
