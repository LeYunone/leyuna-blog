package com.leyuna.blog.service.file;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.disk.DiskCO;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.error.UserAsserts;
import com.leyuna.blog.rpc.command.DiskFileExe;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

    @Value("${disk.max.memory}")
    private Long maxMemory;

    public Page<FileInfoCO> selectFile(FileQueryBean queryBean){
          return fileExe.selectFile(queryBean);
    }

    public ResponseBean requestSaveFile(List<MultipartFile> file){
        AssertUtil.isFalse(CollectionUtils.isEmpty(file), UserAsserts.UPLOAD_NOT_FILE.getMsg());
        //用户编号
        String userId= (String) StpUtil.getLoginId();
        UpFileBean fileBean=new UpFileBean();
        fileBean.setFiles(file);
        fileBean.setUserId(userId);
        //得到过滤成功后的文件
        ResponseBean<Integer> listResponseBean = fileExe.requestSaveFile(fileBean);
        Integer data = listResponseBean.getData();
        //0 是服务器内部处理文件    1 是需要在服务器生成文件
        return ResponseBean.of(data);
    }

    public ResponseBean saveFile(UpFileBean fileBean){
        return fileExe.saveFile(fileBean);
    }

    public ResponseBean deleteFile(String id){
        return fileExe.deleteFile(id);
    }
    public ResponseEntity downloadFile(String id){
        return fileExe.downloadFile(id);
    }

    public DiskCO getFileList(String userId){
        DiskCO diskCO=new DiskCO();
        //用户当前文件列表
        Page<FileInfoCO> fileInfoCOPage = fileExe.selectFile(FileQueryBean.builder().userId(userId).type(3).build());
        //初始化页面时，默认取前十条展示到所有文件
        List<FileInfoCO> fileInfos = fileInfoCOPage.getRecords();
        diskCO.setFileList(fileInfos);
        //获取内存占有量
        Long totalSize=CollectionUtils.isEmpty(fileInfos)?0:fileInfos.get(0).getFileSizeTotal();
        diskCO.setFileTotalSize(totalSize/maxMemory);
        diskCO.setFileCount(fileInfoCOPage.getTotal());
        return diskCO;
    }

    /**
     * 用户上传文件
     * @param file
     */
    public ResponseBean uploadFile(List<MultipartFile> file){
        AssertUtil.isFalse(CollectionUtils.isEmpty(file), UserAsserts.UPLOAD_NOT_FILE.getMsg());
        //用户编号
        String userId= (String) StpUtil.getLoginId();
        UpFileBean fileBean=new UpFileBean();
        fileBean.setFiles(file);
        fileBean.setUserId(userId);
        //上传文件流程
        ResponseBean responseBean = fileExe.saveFile(fileBean);
        return responseBean;
    }
}
