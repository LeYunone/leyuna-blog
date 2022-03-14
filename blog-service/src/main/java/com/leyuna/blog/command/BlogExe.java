package com.leyuna.blog.command;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.domainservice.BlogDomainService;
import com.leyuna.blog.error.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Autowired
    private BlogDomainService blogDomainService;

    /**
     * 添加博客
     * @param
     * @return
     */
    public void addBlog(BlogBean blog){
        String[] tags = blog.getTags();
        if(tags.length!=0){
            StringBuilder stringBuilder=new StringBuilder();
            for(String tag:tags){
                stringBuilder.append(tag+",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            blog.setTag(stringBuilder.toString());
        }
        BlogE blogDTO = BlogE.of(blog);
        blogDTO.setClickCount(0);
        blogDTO.setCommentCount(0);
        blogDTO.setCreateTime(LocalDateTime.now());
        blogDTO.setUpdateTime(LocalDateTime.now());
        BlogCO save = blogDTO.save();
        AssertUtil.isFalse(null == save , SystemErrorEnum.ADD_BLOG_FAIL.getMsg());

        //后置内容
        blogDomainService.addBlogAfter(blogDTO);
    }

    /**
     * 支持模糊查询 分页  获得所有博客
     * @return
     */
    @Cacheable(value = "getAllBlogByPage")
    public DataResponse getAllBlogByPage(BlogBean blogBean){
        Page<BlogCO> blogPage=BlogE.queryInstance().getGateway().queryBlog(blogBean);
        return DataResponse.of(blogPage);
    }

    public boolean updateBlog(BlogBean blogDTO){
        BlogE blog = BlogE.of(blogDTO);
        return blog.update();
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
