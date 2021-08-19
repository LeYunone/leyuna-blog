package com.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.domain.BlogDomain;
import com.blog.api.dto.BlogDTO;
import com.blog.api.dto.ResultDTO;
import com.blog.bean.BlogBean;
import com.blog.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengli
 * @create 2021-08-10 15:04
    博客控制器- 控制博客行为
 */
@RestController()
@RequestMapping("/blog")
public class BlogControl extends SysBaseControl {

    @Autowired
    private BlogDomain blogDomain;
    /**
     * 发布博客
     * @param blogBean
     * @return
     */
    @RequestMapping("/addBlog")
    public ResponseBean addBlog(BlogBean blogBean){

        return null;
    }

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogs")
    public ResponseBean getAllBlogs(Integer index,Integer size){
        Page<BlogDTO> blogsByPage = blogDomain.getBlogsByPage(index, size, null, null);
        return successResponseBean(blogsByPage);
    }
}
