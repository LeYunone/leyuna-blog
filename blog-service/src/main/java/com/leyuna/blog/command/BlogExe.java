package com.leyuna.blog.command;

import com.alibaba.nacos.api.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.domainservice.BlogDomainService;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.UpLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        //处理标签  组装逗号分隔的标签字符
        if (tags!=null && tags.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String tag : tags) {
                stringBuilder.append(tag + ",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            blog.setTag(stringBuilder.toString());
        }
        BlogE blogDTO = BlogE.of(blog);
        blogDTO.setCommentCount(0);
        //添加博客
        BlogCO save = null;
        try {
            save = blogDTO.save();
        } catch (Exception e) {
            e.printStackTrace();
                AssertUtil.isTrue(SystemErrorEnum.ADD_BLOG_FAIL.getMsg() + ":可能是文章中有特殊符号无法被数据库接受，比如emoJi？");
        }

        //后置内容处理  标签添加  分页添加  缓存刷新
        blogDomainService.addBlogAfter(blogDTO);
        return save;
    }

    /**
     * 添加番剧评测
     *
     * @param blog
     */
    public BlogCO addAnime (BlogBean blog) {
        if(null!=blog.getCover()){
            //上传封面至服务器
            UpLoadUtil.uploadFile(ServerCode.COVER_SAVE_PATH,blog.getCover());
        }
        String blogCover = blog.getCover().getOriginalFilename();
        //组装封面地址
        blogCover = ServerCode.SERVER_COVER_SAVE_PATH + blogCover;
        blog.setBlogCover(blogCover);
        return BlogE.of(blog).save();
    }

    /**
     * 添加公告
     *
     * @param blog
     */
    public BlogCO addNotice (BlogBean blog) {
        return BlogE.of(blog).save();
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

    /**
     * id更新博客
     *
     * @param blogDTO
     */
    @CacheEvict(cacheNames = "blog", key = "#blogDTO.id")
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

    /**
     * 随机获取刷题日记
     *
     * @return
     */
    public List<BlogCO> getRandomLeetCodeLog () {
        List<BlogCO> blogCOS = BlogE.selectRandomList();
        return blogCOS;
    }
}
