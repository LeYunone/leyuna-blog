package com.blog.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author pengli
 * @create 2021-08-31 09:49
 *
 * 文件上传工具类
 */
public class UpLoadUtil {

    /**
     * 服务器上传图片， 目录为日期文件夹
     * @param file
     * @param data
     * @return
     */
    public static boolean imgUpLoadFromClient(MultipartFile file,String data){
        String fileName=file.getOriginalFilename();
        //目的服务器存储文件的位置
        String path="c:/img/";
        File serverFile=new File(path+data+"/"+fileName);
        if(!serverFile.getParentFile().exists()){
            //创建服务器日期文件夹
            serverFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(serverFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
