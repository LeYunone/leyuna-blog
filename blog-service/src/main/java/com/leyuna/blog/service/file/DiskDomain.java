package com.leyuna.blog.service.file;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.disk.DiskCO;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.rpc.command.DiskFileExe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengli
 * @create 2021-12-24 11:03
 */
@Service
public class DiskDomain {

    @Autowired
    private DiskFileExe fileExe;

    public Page<FileInfoCO> selectFile(FileQueryBean queryBean){
          return fileExe.selectFile(queryBean);
    }

    public ResponseBean requestSaveFile(UpFileBean fileBean){
        return fileExe.requestSaveFile(fileBean);
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
        diskCO.setFileTotalSize(CollectionUtils.isEmpty(fileInfos)?0:fileInfos.get(0).getFileSizeTotal());
        diskCO.setFileCount(fileInfoCOPage.getTotal());
        return diskCO;
    }
}
