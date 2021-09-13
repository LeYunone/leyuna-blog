package com.blog.api.command;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.dto.BlogDTO;
import com.blog.api.dto.WebHistoryDTO;
import com.blog.daoservice.dao.BlogDao;
import com.blog.daoservice.dao.WebHistoryDao;
import com.blog.daoservice.entry.Blog;
import com.blog.daoservice.entry.WebHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import util.TransformationUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author pengli
 * @create 2021-08-16 13:33
 *
 * 博客 操作指令  缓存方法存储区
 */
@Service
public class BlogExe {

    @Autowired
    private BlogDao blogDao;

    // redis緩存
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加博客
     * @param blogDTO
     * @return
     */
    public Integer addBlog(BlogDTO blogDTO){
        Blog blog = TransformationUtil.copyToDTO(blogDTO, Blog.class);
        boolean save = blogDao.save(blog);
        if(save){
            return blog.getId();
        }
        return null;
    }

    /**
     * 添加点击量
     * @param blogId
     * @param clickCount
     * @return
     */
    public boolean addBlogClickCount(Integer blogId,Integer clickCount){
        boolean save = blogDao.updateClickCount(blogId,clickCount);
        return save;
    }

    /**
     * 查询所有博客
     * @return
     */
    public List<BlogDTO> getAllBlog(){
        List<Blog> blogs = blogDao.queryByCon(new Blog());
        List<BlogDTO> blogDTOS = TransformationUtil.copyToLists(blogs, BlogDTO.class);
        return blogDTOS;
    }

    /**
     * 支持模糊查询 分页  获得所有博客
     * @param index
     * @param size
     * @param conditionName
     * @return
     */
    @Cacheable(value = "getAllBlogByPage")
    public Page<BlogDTO> getAllBlogByPage(Integer index,Integer size,String conditionName){
        Page<Blog> page=new Page(index,size);
        IPage<Blog> blogIPage =null;
        if(StringUtils.isNotEmpty(conditionName)){
            blogIPage=blogDao.queryByBlogName(conditionName, page);
        }else{
            blogIPage=blogDao.queryByConPageOrderCreateTime(new Blog(), page,0);
        }
        //转换结果集
        Page<BlogDTO> blogDTOPage=new Page<>(index,size);
        blogDTOPage.setRecords(TransformationUtil.copyToLists(blogIPage.getRecords(), BlogDTO.class));
        blogDTOPage.setTotal(page.getTotal());
        return blogDTOPage;
    }

    /**
     * 根据 分类或标签 分页查询博客  支持模糊查询
     * @param index
     * @param size
     * @param type
     * @param tag
     * @param conditionName
     * @return
     */
    @Cacheable(cacheNames = "getBlogByPage")
    public Page<BlogDTO> getBlogByPage(Integer index,Integer size,Integer type,String tag,String conditionName){
        Page<Blog> page=new Page(index,size);
        IPage<Blog> blogIPage =null;
        if(null==type){
            //根据标签查询  名字里包含
            blogIPage = blogDao.queryByTagName(Blog.builder().tag(tag).build(), page);
        }else{
            //根据分类查询
            blogIPage = blogDao.queryByConPageOrderCreateTime(Blog.builder().type(type).build(), page,0);
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
    @Cacheable(cacheNames = "getBlogById")
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
