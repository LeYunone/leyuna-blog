package com.leyuna.blog.control;

import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.co.blog.LuceneCO;
import com.leyuna.blog.service.BlogService;
import com.leyuna.blog.service.SearchService;
import com.leyuna.blog.util.FtpUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author pengli
 * @create 2021-08-10 15:04
    博客控制器- 控制博客行为 网站公告行为 以及各种公示行为
 */
@RestController()
@RequestMapping("/blog")
public class BlogControl{

    @Autowired
    private BlogService blogService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private FtpUploadUtil ftpUploadUtil;
    /**
     * 发布博客
     * @param blogDTO
     * @return
     */
    @PostMapping("/addBlog")
    public DataResponse addBlog( BlogDTO blogDTO){
        return blogService.addBlog(blogDTO);
    }

    @GetMapping("/test")
    public void test() throws FileNotFoundException {
        //纯测试接口  当前测试：Ftp客户端文件上传
        FileInputStream fis=new FileInputStream(new File("C://img/wx.png"));
        ftpUploadUtil.upload("C:/test/",fis,"test.png");
    }

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogs")
    public DataResponse blogs(BlogDTO blogDTO){
        return blogService.getBlogsByPage(blogDTO);
    }

    @GetMapping("/blog/{id}")
    public DataResponse<BlogCO> getBlogById(@PathVariable(value = "id")String blogId){
        return blogService.getBlogById(blogId);
    }

    @PostMapping("/edit")
    public DataResponse editBlog(@RequestBody BlogDTO blogDTO){
        return blogService.updateBlog(blogDTO);
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
        return searchService.createBlogSearch();
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
        return blogService.getLeetCode();
    }
}
