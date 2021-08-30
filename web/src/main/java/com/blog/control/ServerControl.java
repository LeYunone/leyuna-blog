package com.blog.control;

import com.blog.bean.FileBean;
import com.blog.bean.ResponseBean;
import com.mysql.cj.jdbc.Blob;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author pengli
 * @create 2021-08-30 16:42
 *  服务器相关控制器  比如文件服务器
 */
@RestController
@RequestMapping("/server")
public class ServerControl extends SysBaseControl {

    @PostMapping("/updownimg")
    public ResponseBean upDownImgToServer(@RequestBody FileBean file){
        String path="D/";
        File img=new File(path+file.getName());
        //上传服务器

        return null;
    }
}
