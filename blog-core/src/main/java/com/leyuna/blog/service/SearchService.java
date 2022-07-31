package com.leyuna.blog.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.model.co.LuceneCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author pengli
 * @create 2021-09-09 10:24
 *
 *  搜索[爬虫、站内] 活动领域
 */
@Service
public class SearchService {

    @Autowired
    private LuceneService luceneService;

    /**
     * 创建所有blog的索引库
     */
    public void createBlogSearch(){
        luceneService.addBlogDir(null);
    }

    /**
     * 分页查询站内博客
     * @return
     */
    public DataResponse<LuceneCO> getBlogFromSearch(BlogDTO blogDTO){
        return luceneService.getBlogDir(blogDTO.getTitle(), blogDTO.getIndex(), blogDTO.getSize());
    }

    public static List<String> emoList = new Stack<>();

    /**
     * 搜索站内表情包
     * @return
     */
    public DataResponse getEmoticon(){
        //利用最低限度缓存
        if(CollectionUtils.isNotEmpty(SearchService.emoList)){
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
        SearchService.emoList=result;
        return DataResponse.of(result);
    }


    /**
     * 迭代文件夹目录
     * @param file
     * @param result
     * @param path
     */
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
