package com.blog.api.domain;

import com.blog.api.command.BlogExe;
import com.blog.api.command.LuceneExe;
import com.blog.api.dto.BlogDTO;
import org.apache.lucene.queryparser.classic.ParseException;
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
        List<BlogDTO> allBlog = blogExe.getAllBlog();
        //创建blog的索引库  field有id 和title
        try {
            luceneExe.addBlogDir(allBlog);
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
    public List<BlogDTO> getBlogFromSearch(String key,Integer index,Integer size){
        List<BlogDTO> blogDir=null;
        try {
            blogDir = luceneExe.getBlogDir(key, index, size);
        }catch (IOException | ParseException e){

        }
        return blogDir;
    }
}
