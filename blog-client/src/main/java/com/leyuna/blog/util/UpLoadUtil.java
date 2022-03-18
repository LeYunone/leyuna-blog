package com.leyuna.blog.util;

import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    /**
     * 客户端上传图片工具
     * @param file
     * @return
     */
    public static void imgUpLoadFromClient(MultipartFile file,String suf,String name,String sufPath){
        //目的服务器存储文件的位置
        String path= ServerCode.IMG_SAVE_PATH;
        path=path+sufPath;
        File serverFile=null;
        if(StringUtils.isEmpty(suf)){
            serverFile=new File(path+"/"+name);
        }else{
            serverFile=new File(path+"/"+name+suf);
        }
        if(!serverFile.getParentFile().exists()){
            //创建服务器日期文件夹
            serverFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(serverFile);
        } catch (IOException e) {
            AssertUtil.isFalse(true, SystemErrorEnum.UPLOCAD_IMG_FAIL.getMsg());
        }
    }
}
