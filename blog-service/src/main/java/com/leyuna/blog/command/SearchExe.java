package com.leyuna.blog.command;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengli
 * @date 2022-03-23
 * 
 * 搜索指令
 */
@Service
public class SearchExe {

    /**
     * 获得路径下所有文件名
     * @return
     */
    public DataResponse searchEmoImg () {
        List<String> result=new ArrayList<>();
        
        String path = ServerCode.EMO_SAVE_PATH;
        File thisFile = new File(path);
        AssertUtil.isFalse(!thisFile.exists(),"操作失败：服务器内没有emo文件");
        
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
        return DataResponse.of(result);
    }

    private void orderFolder(File file, List<String> result){
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
