package com.leyuna.blog.service;

import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.LuceneCO;
import com.leyuna.blog.command.LuceneExe;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.util.UpLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author pengli
 * @create 2021-09-09 10:24
 *
 *  搜索[爬虫、站内] 活动领域
 */
@Service
public class SearchService {

    @Autowired
    private LuceneExe luceneExe;

    public DataResponse createBlogSearch(){
        //创建所有blog的索引库
        luceneExe.addBlogDir(null);
        return DataResponse.buildSuccess();
    }

    /**
     * 分页查询站内博客
     * @return
     */
    public DataResponse<LuceneCO> getBlogFromSearch(BlogBean blogBean){
        return luceneExe.getBlogDir(blogBean.getTitle(), blogBean.getIndex(), blogBean.getSize());
    }

    /**
     * 搜索站内表情包
     * @return
     */
    public DataResponse getEmoticon(){
        if(CollectionUtils.isEmpty(UpLoadUtil.emoList)){
            String emoPath = ServerCode.EMO_SAVE_PATH;
            List<String> folderFileStr = UpLoadUtil.getFolderFileStr(emoPath);
            UpLoadUtil.emoList = folderFileStr;
        }
        return DataResponse.of(UpLoadUtil.emoList);
    }
}
