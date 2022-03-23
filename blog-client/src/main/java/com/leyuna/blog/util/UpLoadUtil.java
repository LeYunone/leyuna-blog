package com.leyuna.blog.util;

import com.leyuna.blog.constant.enums.SystemErrorEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author pengli
 * @create 2021-08-31 09:49
 * <p>
 * 文件上传工具类
 */
public class UpLoadUtil {

    public static List<String> emoList = new Stack<>();

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
            AssertUtil.isFalse(true, SystemErrorEnum.UPLOCAD_IMG_FAIL.getMsg());
        }
    }

    /**
     * 获得路径下所有文件名
     * @param path
     * @return
     */
    public static List<String> getFolderFileStr (String path) {
        List<String> result=new ArrayList<>();
        File thisFile = new File(path);
        if(!thisFile.exists()){
            return result;
        }
        File[] files = thisFile.listFiles();
        if(files.length!=0){
            for(File file:files){
                if(file.isFile()){
                    result.add(file.getName());
                }
                if(file.isDirectory()){
                    orderFolder(file,result);
                }
            }
        }
        return result;
    }

    private static void orderFolder(File file,List<String> result){
        if(file.isFile()){
            result.add(file.getName());
        }else{
            File[] files = file.listFiles();
            for(File f:files){
                orderFolder(f,result);
            }
        }
    }
}
