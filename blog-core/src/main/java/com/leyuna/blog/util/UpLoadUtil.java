package com.leyuna.blog.util;

import com.leyuna.blog.constant.code.SystemErrorEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author pengli
 * @create 2021-08-31 09:49
 * <p>
 * 文件上传工具类
 */
public class UpLoadUtil {

    public static void uploadFile (String path, MultipartFile file) {
        //目的服务器存储文件的位置
        File serverFile = new File(path + "/" + file.getOriginalFilename());
        if (!serverFile.getParentFile().exists()) {
            //创建服务器日期文件夹
            serverFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
            AssertUtil.isFalse(true, SystemErrorEnum.UPLOCAD_IMG_FAIL.getMsg());
        }
    }
}
