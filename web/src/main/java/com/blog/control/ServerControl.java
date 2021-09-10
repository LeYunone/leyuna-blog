package com.blog.control;

import com.blog.api.domain.UserDomain;
import com.blog.bean.ResponseBean;
import com.blog.error.SystemAsserts;
import com.blog.util.UpLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author pengli
 * @create 2021-08-30 16:42
 *  服务器相关控制器  比如文件服务器
 */
@RestController
@RequestMapping("/server")
public class ServerControl extends SysBaseControl {

    @Autowired
    private UserDomain userDomain;
    @PostMapping("/updownimg")
    public ResponseBean upDownImgToServer( MultipartFile file) {
        userDomain.checkLock();
        //上传服务器
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        boolean b = UpLoadUtil.imgUpLoadFromClient(file,format);
        if(b){
            return successResponseBean(format+"/"+file.getOriginalFilename());
        }else{
            return failResponseBean(SystemAsserts.UPLOCAD_IMG_FAIL.getMsg());
        }
    }
}
