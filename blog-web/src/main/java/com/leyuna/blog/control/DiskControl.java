package com.leyuna.blog.control;

import cn.dev33.satoken.stp.StpUtil;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.co.disk.DiskCO;
import com.leyuna.blog.service.file.DiskDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author pengli
 * @create 2021-12-28 14:01
 * 云盘模型控制器
 *
 * AOP完成用户权限校验
 */
@RestController
@RequestMapping("/disk")
public class DiskControl {

    @Autowired
    private DiskDomain diskDomain;

    /**
     * 获取当前用户的云盘信息
     * @return
     */
    @GetMapping("/getDiskInfo")
    public ResponseBean getDiskInfo(){
        UserCO user = (UserCO)StpUtil.getSession().get("user");
        //开始组装云盘初始信息源
        DiskCO fileList = diskDomain.getFileList(user.getId());
        return ResponseBean.of(fileList);
    }

    /**
     * 上传文件
     * @param files
     * @return
     */
    @PostMapping("/uploadFile")
    public ResponseBean uploadFile(List<MultipartFile> file){
        return diskDomain.uploadFile(file);
    }
}
