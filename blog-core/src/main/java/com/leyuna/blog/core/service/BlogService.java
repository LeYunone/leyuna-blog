package com.leyuna.blog.core.service;

import com.leyuna.blog.core.dao.BlogDao;
import com.leyuna.blog.core.model.co.BlogCO;
import com.leyuna.blog.core.model.constant.DataResponse;
import com.leyuna.blog.core.model.dto.BlogDTO;
import com.leyuna.blog.core.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengli
 * @create 2021-08-16 13:29
 * <p>
 * 博客领域
 */
@Service
public class BlogService {

    @Autowired
    private BlogDao blogDao;


    /**
     * 分页取得博客
     *
     * @return
     */
    public DataResponse getBlogsByPage (BlogDTO blogDTO) {
        return blogExe.getAllBlogByPage(blogDTO);
    }

    /**
     * 发布博客
     *
     * @return
     */
    @Transactional
    @CacheEvict(cacheNames = {"blog:blogs", "blog:type", "blog:tag"}, allEntries = true)
    public DataResponse addBlog (BlogDTO blog) {
        Integer blogType = blog.getBlogType();

        BlogTypeEnum.BLOG_TYPE.getValue();
        BlogCO blogCO = null;
        switch (BlogTypeEnum.loadName(blogType)) {
            case BLOG_TYPE:
                //添加博客
                blogCO = blogExe.addBlog(blog);
                //添加改博客的索引库文档
                List<BlogDTO> list = new ArrayList<>();
                blog.setId(blogCO.getId());
                list.add(blog);
                luceneExe.addBlogDir(list);
                break;
            case NOTICE_TYPE:
                blogCO = blogExe.addNotice(blog);
                break;
            case ANIME_TYPE:
                blogCO = blogExe.addAnime(blog);
                break;
            default:
        }
        AssertUtil.isFalse(null == blogCO, SystemErrorEnum.ADD_BLOG_FAIL.getMsg());
        return DataResponse.buildSuccess();
    }

    /**
     * 打开文章
     *
     * @param id
     * @return
     */
    public DataResponse<BlogCO> getBlogById (String id) {
        blogDao.selectById(id);
        return
    }

    /**
     * 更新文章
     *
     * @return
     */
    public DataResponse updateBlog (BlogDTO blog) {
        Integer blogType = blog.getBlogType();
        switch (BlogTypeEnum.loadName(blogType)){
            case BLOG_TYPE:
                blogExe.updateBlog(blog);
                //更新索引库中的Key
                luceneExe.updateBlogDocument(blog);
                break;
            case NOTICE_TYPE:
                blogExe.updateBlog(blog);
                break;
        }
        return DataResponse.buildSuccess();
    }

    /**
     * 获取刷题日记  -  leetcode题  随机  爬虫
     * @return
     */
    public DataResponse getLeetCode(){
        //随机获取刷题日记
        List<BlogCO> randomLeetCodeLog = blogExe.getRandomLeetCodeLog();
        return DataResponse.of(randomLeetCodeLog);
    }
}
