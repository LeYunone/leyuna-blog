package com.leyuna.blog.control;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.model.co.BlogCO;
import com.leyuna.blog.model.co.LuceneCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.model.query.BlogQuery;
import com.leyuna.blog.service.BlogService;
import com.leyuna.blog.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-10 15:04
    博客控制器- 控制博客行为 网站公告行为 以及各种公示行为
 */
@RestController
@RequestMapping("/blog")
public class BlogControl{

    @Autowired
    private BlogService blogService;
    @Autowired
    private SearchService searchService;
    /**
     * 发布博客
     * @param blogDTO
     * @return
     */
    @PostMapping("/addBlog")
    public DataResponse addBlog(BlogDTO blogDTO){
        blogService.addBlog(blogDTO);
        return DataResponse.buildSuccess();
    }

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogs")
    public DataResponse blogs(BlogDTO blogDTO){
        Page<BlogCO> blogsByPage = blogService.getBlogsByPage(blogDTO);
        return DataResponse.of(blogsByPage);
    }

    @GetMapping("/blog/{id}")
    public DataResponse<BlogCO> getBlogById(@PathVariable(value = "id")String blogId){
        BlogCO blogById = blogService.getBlogById(blogId);
        return DataResponse.of(blogById);
    }

    @PostMapping("/edit")
    public DataResponse editBlog(@RequestBody BlogDTO blogDTO){
        blogService.updateBlog(blogDTO);
        return DataResponse.buildSuccess();
    }

    /**
     * 站内搜索博客
     * @return
     */
    @GetMapping("/search")
    public DataResponse<LuceneCO> blogSearch(BlogDTO blogDTO){
        return searchService.getBlogFromSearch(blogDTO);
    }

    /**
     * 创建当前数据库中所有博客的索引库
     * @return
     */
    @PostMapping("/createDocument")
    public DataResponse createAllBlogDocument(){
        searchService.createBlogSearch();
        return DataResponse.buildSuccess();
    }

    /**
     * 获得服务器里的表情包
     * @return
     */
    @GetMapping("/getEmoticon")
    public DataResponse getEmoticon(){
        return searchService.getEmoticon();
    }

    /**
     * 服务leetcode展示面板，随机取出 题目 - 刷题日记
     * @return
     */
    @GetMapping("/getLeetCode")
    public DataResponse getLeetCode(){
        List<com.leyuna.blog.model.co.BlogCO> leetCode = blogService.getLeetCode();
        return DataResponse.of(leetCode);
    }

    @GetMapping("/getTopMenuBlogs")
    public DataResponse getTopMenuBlogs(BlogQuery blogQuery){
        IPage<BlogCO> topMenuBlogs = blogService.getTopMenuBlogs(blogQuery);
        return DataResponse.of(topMenuBlogs);
    }
}
