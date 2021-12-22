package com.leyuna.blog.command;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyuna.blog.co.BlogCO;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.entry.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-16 13:33
 *
 * 博客 操作指令  缓存方法存储区
 */
@Service
public class BlogExe {

    // redis緩存
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加博客
     * @param blogDTO
     * @return
     */
    public String addBlog(BlogE blogDTO){
        BlogCO save = blogDTO.save();
        if(null!=save){
            return save.getId();
        }
        return null;
    }

    /**
     * 添加点击量
     * @param blogId
     * @param clickCount
     * @return
     */
    public boolean addBlogClickCount(String blogId,Integer clickCount){
        boolean save = BlogE.queryInstance().getGateway().updateClickCount(blogId,clickCount);
        return save;
    }

    /**
     * 查询所有博客
     * @return
     */
    public List<BlogCO> queryAllBlog(){
        List<BlogCO> blogs = BlogE.queryInstance().selectByCon();
        return blogs;
    }

    /**
     * 支持模糊查询 分页  获得所有博客
     * @param index
     * @param size
     * @param conditionName
     * @return
     */
    @Cacheable(value = "getAllBlogByPage")
    public IPage<BlogCO> getAllBlogByPage(Integer index,Integer size,String conditionName){
        IPage<BlogCO> blogIPage =null;
        if(StringUtils.isNotEmpty(conditionName)){
            blogIPage=BlogE.queryInstance().getGateway().queryByBlogName(conditionName, index,size);
        }else{
            blogIPage=BlogE.queryInstance().getGateway().selectByConOrderPage(new Blog(), index,size,2);
        }
        return blogIPage;
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
    public IPage<BlogCO> getBlogByPage(Integer index,Integer size,Integer type,String tag,String conditionName){
        IPage<BlogCO> blogIPage =null;
        if(null==type){
            //根据标签查询  名字里包含
            blogIPage = BlogE.queryInstance().getGateway().queryByTagName(BlogCO.builder().tag(tag).build(), index,size);
        }else{
            //根据分类查询
            blogIPage = BlogE.queryInstance().getGateway().selectByConOrderPage(BlogCO.builder().type(type).build(), index,size,0);
        }
        //转换结果集
        return blogIPage;
    }

    /**
     * 根据id查询博客  确定能查到直接返回结果
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "getBlogById")
    public BlogCO getBlogById(String id){
        BlogCO blog = BlogE.queryInstance().setId(id).selectById();
        return blog;
    }

    public boolean updateBlog(BlogE blogDTO){
        boolean update = blogDTO.update();
        return update;
    }
}
