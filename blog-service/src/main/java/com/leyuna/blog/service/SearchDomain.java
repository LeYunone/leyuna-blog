package com.leyuna.blog.service;

import com.leyuna.blog.co.BlogCO;
import com.leyuna.blog.co.LuceneCO;
import com.leyuna.blog.command.BlogExe;
import com.leyuna.blog.command.LuceneExe;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author pengli
 * @create 2021-09-09 10:24
 *
 *  搜索[爬虫、站内] 活动领域
 */
@Service
public class SearchDomain {

    @Autowired
    private LuceneExe luceneExe;

    @Autowired
    private BlogExe blogExe;
    /**
     * 创建blog的索引库
     */
    public boolean createBlogSearch(){
        //查询所有博客
        List<BlogCO> allBlog = blogExe.queryAllBlog();
        //创建blog的索引库  field有id 和title
        try {
            luceneExe.addBlogDir(TransformationUtil.copyToLists(allBlog, BlogE.class));
        }catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 分页查询站内博客
     * @param key
     * @param index
     * @param size
     * @return
     */
    public LuceneCO getBlogFromSearch(String key, Integer index, Integer size){
        LuceneCO blogDir=null;
        try {
            blogDir = luceneExe.getBlogDir(key, index, size);
        }catch (IOException | ParseException | InvalidTokenOffsetsException e){

        }
        return blogDir;
    }
}
