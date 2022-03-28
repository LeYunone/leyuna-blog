package com.leyuna.blog.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.constant.enums.UserErrorEnum;
import com.leyuna.blog.rpc.command.DiskFileExe;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    public DataResponse selectFile (FileQueryBean queryBean) {
        return fileExe.selectFile(queryBean);
    }

    public DataResponse requestSaveFile (List<MultipartFile> file) {
        return fileExe.requestSaveFile(file);
    }

    public DataResponse deleteFile (String id) {
        //删除文件
        fileExe.deleteFile(id);

        //删除之后获得最新的总内存大小
        return fileExe.selectAllFileSize();
    }

    public FileInfoCO downloadFile (String id) {
        return fileExe.downloadFile(id);
    }

    /**
     * 用户上传文件
     *
     * @param file
     */
    public DataResponse uploadFile (List<MultipartFile> file, String saveTime) {
        AssertUtil.isFalse(CollectionUtils.isEmpty(file), UserErrorEnum.UPLOAD_NOT_FILE.getMsg());
        UpFileBean fileBean = new UpFileBean();
        fileBean.setSaveTime(saveTime);
        fileBean.setFiles(file);
        //上传文件流程
        fileExe.saveFile(fileBean);

        return fileExe.selectFile(new FileQueryBean());
    }
}
