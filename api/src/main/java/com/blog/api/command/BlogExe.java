package com.blog.api.command;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.dto.BlogDTO;
import com.blog.api.dto.TypeDTO;
import com.blog.daoservice.dao.BlogDao;
import com.blog.daoservice.entry.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.TransformationUtil;

/**
 * @author pengli
 * @create 2021-08-16 13:33
 *
 * 博客 操作指令
 */
@Service
public class BlogExe {

    @Autowired
    private BlogDao blogDao;

    public boolean addBlog(BlogDTO blogDTO){
        return true;
    }

    public Page<BlogDTO> getAllBlogByPage(Integer index,Integer size){
        Page<Blog> page=new Page(index,size);
        IPage<Blog> blogIPage = blogDao.queryByConPage(new Blog(), page);
        //转换结果集
        Page<BlogDTO> blogDTOPage=new Page<>(index,size);
        blogDTOPage.setRecords(TransformationUtil.copyToLists(blogIPage.getRecords(), BlogDTO.class));
        return blogDTOPage;
    }

    public Page<BlogDTO> getBlogByPage(Integer index,Integer size,Integer tag,Integer type){
        Page<Blog> page=new Page(index,size);
        IPage<Blog> blogIPage = blogDao.queryByConPage(Blog.builder().tag(tag).type(type).build(), page);
        //转换结果集
        Page<BlogDTO> blogDTOPage=new Page<>(index,size);
        blogDTOPage.setRecords(TransformationUtil.copyToLists(blogIPage.getRecords(), BlogDTO.class));
        return blogDTOPage;
    }
}
