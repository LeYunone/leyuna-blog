package com.leyuna.blog.command;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author pengli
 * @date 2022-03-23
 * 
 * 搜索指令
 */
@Service
public class SearchExe {

    public static List<String> emoList = new Stack<>();

    /**
     * 获得路径下所有文件名
     * @return
     */
    public DataResponse searchEmoImg () {
        //利用最低限度缓存
        if(CollectionUtils.isNotEmpty(SearchExe.emoList)){
            return DataResponse.of(emoList);
        }
        List<String> result=new ArrayList<>();
        String path = ServerCode.SERVER_EMO_SAVE_PATH;
        File thisFile = new File(ServerCode.EMO_SAVE_PATH);
        AssertUtil.isFalse(!thisFile.exists(),"操作失败：服务器内没有emo文件");
        
        File[] files = thisFile.listFiles();
        if(files.length!=0){
            for(File file:files){
                if(file.isFile()){
                    result.add(path+file.getName());
                }
                if(file.isDirectory()){
                    orderEmoFolder(file,result,path);
                }
            }
        }
        //存储
        SearchExe.emoList=result;
        return DataResponse.of(result);
    }

    private void orderEmoFolder(File file, List<String> result,String path){
        if(file.isFile()){
            result.add(path+file.getName());
        }else{
            File[] files = file.listFiles();
            for(File f:files){
                orderEmoFolder(f,result,path);
            }
        }
    }
}
