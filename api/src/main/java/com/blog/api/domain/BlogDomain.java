package com.blog.api.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.command.BlogExe;
import com.blog.api.dto.BlogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-16 13:29
 *
 * 博客领域
 */
@Service
public class BlogDomain {

    @Autowired
    private BlogExe blogExe;
    /**
     * 分页取得博客   根据分类或者标签或者查所有
     * @param index
     * @param size
     * @param type
     * @param tags
     * @return
     */
    public Page<BlogDTO> getBlogsByPage(Integer index,Integer size,Integer type,Integer tags){
        Page<BlogDTO> result=null;
        //查询所有
        if(type==null && tags==null){
            result = blogExe.getAllBlogByPage(index, size);
        }else{
            result = blogExe.getBlogByPage(index, size, type, tags);
        }
        return result;
    }
}
