package com.blog.control;

import com.blog.api.dto.ResultDTO;
import com.blog.bean.BlogBean;
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

    /**
     * 发布博客
     * @param blogBean
     * @return
     */
    @RequestMapping("/addBlog")
    public ResultDTO addBlog(BlogBean blogBean){

        return null;
    }
}
