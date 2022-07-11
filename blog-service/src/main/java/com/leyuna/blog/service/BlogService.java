package com.leyuna.blog.service;

import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.command.BlogExe;
import com.leyuna.blog.command.LuceneExe;
import com.leyuna.blog.constant.enums.BlogTypeEnum;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private BlogExe blogExe;
    @Autowired
    private LuceneExe luceneExe;

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
        return blogExe.getBlog(id);
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
