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
        Blog blog = TransformationUtil.copyToDTO(blogDTO, Blog.class);
        boolean save = blogDao.save(blog);
        return save;
    }

    public boolean addBlogClickCount(Integer blogId,Integer clickCount){
        boolean save = blogDao.updateClickCount(blogId,clickCount);
        return save;
    }

    public Page<BlogDTO> getAllBlogByPage(Integer index,Integer size,String conditionName){
        Page<Blog> page=new Page(index,size);
        IPage<Blog> blogIPage = blogDao.queryByConPage(new Blog(), page);
        //转换结果集
        Page<BlogDTO> blogDTOPage=new Page<>(index,size);
        blogDTOPage.setRecords(TransformationUtil.copyToLists(blogIPage.getRecords(), BlogDTO.class));
        blogDTOPage.setTotal(page.getTotal());
        return blogDTOPage;
    }

    public Page<BlogDTO> getBlogByPage(Integer index,Integer size,Integer type,String tag,String conditionName){
        Page<Blog> page=new Page(index,size);
        IPage<Blog> blogIPage =null;
        if(null==type){
            //根据标签查询  名字里包含
            blogIPage = blogDao.queryByTagName(Blog.builder().tag(tag).build(), page);
        }else{
            //根据分类查询
            blogIPage = blogDao.queryByConPage(Blog.builder().type(type).build(), page);
        }
        //转换结果集
        Page<BlogDTO> blogDTOPage=new Page<>(index,size);
        blogDTOPage.setRecords(TransformationUtil.copyToLists(blogIPage.getRecords(), BlogDTO.class));
        blogDTOPage.setTotal(page.getTotal());
        return blogDTOPage;
    }

    /**
     * 根据id查询博客  确定能查到直接返回结果
     * @param id
     * @return
     */
    public BlogDTO getBlogById(Integer id){
        Blog blog = blogDao.queryByid(id);
        BlogDTO blogDTO = TransformationUtil.copyToDTO(blog, BlogDTO.class);
        return blogDTO;
    }


    public int getBlogByTypeCount(Integer type){
        int i = blogDao.queryCountByType(type);
        return i;
    }

    public boolean updateBlog(BlogDTO blogDTO){
        boolean b = blogDao.updateById(TransformationUtil.copyToDTO(blogDTO, Blog.class));
        return b;
    }
}
