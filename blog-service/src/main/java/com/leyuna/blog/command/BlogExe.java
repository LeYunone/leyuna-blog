package com.leyuna.blog.command;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.entry.Blog;
import com.leyuna.blog.error.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
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
    public Page<BlogCO> getAllBlogByPage(Integer index, Integer size, String conditionName){
        Page<BlogCO> blogPage =null;
        if(StringUtils.isNotEmpty(conditionName)){
            blogPage=BlogE.queryInstance().getGateway().queryByBlogName(conditionName, index,size);
        }else{
            blogPage=BlogE.queryInstance().getGateway().selectByConOrderPage(new Blog(), index,size,2);
        }
        return blogPage;
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
    public Page<BlogCO> getBlogByPage(Integer index,Integer size,String type,String tag,String conditionName){
        Page<BlogCO> blogPage =null;
        if(null==type){
            //根据标签查询  名字里包含
            blogPage = BlogE.queryInstance().getGateway().queryByTagName(BlogE.queryInstance().setTag(tag), index,size);
        }else{
            //根据分类查询
            blogPage = BlogE.queryInstance().getGateway().selectByConOrderPage(BlogE.queryInstance().setType(type), index,size,0);
        }
        //转换结果集
        return blogPage;
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

    /**
     * 得到指定博客
     * @param id 博客id
     * @param count 本次添加点击
     * @return
     */
    public DataResponse getBlog(String id, Integer count){
        //找到博客
        BlogCO blogCO = BlogE.queryInstance().setId(id).selectById();
        AssertUtil.isFalse( null == blogCO, SystemErrorEnum.SELECT_NOT_FAIL.getMsg());
        //添加点击量
        if(0!=count){
            BlogE.queryInstance().setId(id).setClickCount(blogCO.getClickCount()+count);
        }
        return DataResponse.of(blogCO);
    }
}
