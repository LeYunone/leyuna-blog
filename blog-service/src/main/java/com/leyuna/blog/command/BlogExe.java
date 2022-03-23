package com.leyuna.blog.command;

import com.alibaba.nacos.api.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.domainservice.BlogDomainService;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-16 13:33
 * <p>
 * 博客 操作指令  缓存方法存储区
 */
@Service
public class BlogExe {

    @Autowired
    private BlogDomainService blogDomainService;

    /**
     * 添加博客
     *
     * @param
     * @return
     */
    public BlogCO addBlog (BlogBean blog) {
        String[] tags = blog.getTags();
        if (tags.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String tag : tags) {
                stringBuilder.append(tag + ",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            blog.setTag(stringBuilder.toString());
        }
        BlogE blogDTO = BlogE.of(blog);
        blogDTO.setCommentCount(0);
        BlogCO save = blogDTO.save();
        AssertUtil.isFalse(null == save, SystemErrorEnum.ADD_BLOG_FAIL.getMsg());

        //后置内容
        blogDomainService.addBlogAfter(blogDTO);

        return save;
    }

    /**
     * 添加公告
     *
     * @param blog
     */
    public void addNotice (BlogBean blog) {
        BlogCO save = BlogE.of(blog).save();
        AssertUtil.isFalse(null == save, SystemErrorEnum.ADD_BLOG_FAIL.getMsg());
    }

    /**
     * 支持模糊查询 分页  获得所有博客
     *
     * @return
     */
    @Cacheable(cacheNames = "blogs", key = "#blogBean.toString()+'-'+#blogBean.index+'-'+#blogBean.size")
    public DataResponse getAllBlogByPage (BlogBean blogBean) {
        Page<BlogCO> blogPage = BlogE.queryInstance().getGateway().queryBlog(blogBean);
        return DataResponse.of(blogPage);
    }

    @CacheEvict(cacheNames = "blog",key = "#blogDTO.id")
    public void updateBlog (BlogBean blogDTO) {
        AssertUtil.isFalse(StringUtils.isBlank(blogDTO.getId()), "操作失败：id can't empty");
        boolean update = BlogE.of(blogDTO).update();
        AssertUtil.isTrue(update, SystemErrorEnum.UPDATE_BLOG_FAIL.getMsg());
    }

    /**
     * 得到指定博客
     *
     * @param id 博客id
     * @return
     */
    @Cacheable(cacheNames = "blog")
    public DataResponse<BlogCO> getBlog (String id) {
        //找到博客
        BlogCO blogCO = BlogE.queryInstance().setId(id).selectById();
        AssertUtil.isFalse(null == blogCO, SystemErrorEnum.SELECT_NOT_FAIL.getMsg());
        return DataResponse.of(blogCO);
    }
}
