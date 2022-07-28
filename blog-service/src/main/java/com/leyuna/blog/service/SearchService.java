package com.leyuna.blog.service;

import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.LuceneCO;
import com.leyuna.blog.command.LuceneExe;
import com.leyuna.blog.command.SearchExe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private SearchExe searchExe;

    public DataResponse createBlogSearch(){
        //创建所有blog的索引库
        luceneExe.addBlogDir(null);
        return DataResponse.buildSuccess();
    }

    /**
     * 分页查询站内博客
     * @return
     */
    public DataResponse<LuceneCO> getBlogFromSearch(BlogDTO blogDTO){
        return luceneExe.getBlogDir(blogDTO.getTitle(), blogDTO.getIndex(), blogDTO.getSize());
    }

    /**
     * 搜索站内表情包
     * @return
     */
    public DataResponse getEmoticon(){
        return searchExe.searchEmoImg();
    }
}
