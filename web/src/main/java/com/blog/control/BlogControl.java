package com.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.domain.BlogDomain;
import com.blog.api.dto.BlogDTO;
import com.blog.bean.BlogBean;
import com.blog.bean.ResponseBean;
import com.blog.error.SystemAsserts;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.TransformationUtil;

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
    public ResponseBean addBlog(@RequestBody BlogBean blogBean){
        System.out.println(blogBean);
        String[] tags = blogBean.getTags();
        if(tags.length!=0){
            StringBuilder stringBuilder=new StringBuilder();
            for(String tag:tags){
                stringBuilder.append(tag+",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            blogBean.setTag(stringBuilder.toString());
        }
        boolean b = blogDomain.addBlog(TransformationUtil.copyToDTO(blogBean, BlogDTO.class));
        if(b){
            return successResponseBean();
        }else{
            return failResponseBean(SystemAsserts.ADD_BLOG_FAIL);
        }
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

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogToType")
    public ResponseBean getAllBlogsByTypeId(Integer index,Integer size,
                                            @RequestParam(required = false) Integer typeId,
                                            @RequestParam(required = false)String tagName){
        Page<BlogDTO> blogsByPage = blogDomain.getBlogsByPage(index, size, typeId, tagName);
        ResponseBean responseBean = successResponseBean(blogsByPage);
        //包装有多少条博客
        int blogsByTypeCount = blogDomain.getBlogsByTypeCount(typeId);
        responseBean.setObjData(blogsByTypeCount);
        return responseBean;
    }

    @GetMapping("/blog/{id}")
    public ResponseBean getBlogById(@PathVariable(value = "id")Integer blogId){
        BlogDTO blogById = blogDomain.getBlogById(blogId);
        return successResponseBean(blogById);
    }
}
