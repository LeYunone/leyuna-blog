package com.leyuna.blog.service;

import com.leyuna.blog.model.co.LuceneCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.model.dto.BlogDTO;
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

}
