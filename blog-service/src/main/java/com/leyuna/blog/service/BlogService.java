package com.leyuna.blog.service;

import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.command.BlogExe;
import com.leyuna.blog.command.LuceneExe;
import com.leyuna.blog.constant.enums.BlogTypeEnum;
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
    public DataResponse getBlogsByPage (BlogBean blogBean) {
        return blogExe.getAllBlogByPage(blogBean);
    }

    /**
     * 发布博客
     *
     * @return
     */
    @Transactional
    @CacheEvict(cacheNames = {"blogs", "type", "tag"}, allEntries = true)
    public DataResponse addBlog (BlogBean blog) {
        Integer blogType = blog.getBlogType();

        BlogTypeEnum.BLOG_TYPE.getValue();
        switch (BlogTypeEnum.loadName(blogType)) {
            case BLOG_TYPE:
                //添加博客
                BlogCO blogCO = blogExe.addBlog(blog);
                //添加改博客的索引库文档
                List<BlogBean> list = new ArrayList<>();
                blog.setId(blogCO.getId());
                list.add(blog);
                luceneExe.addBlogDir(list);
                break;
            case NOTICE_TYPE:
                blogExe.addNotice(blog);
                break;
            default:
        }
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
    public DataResponse updateBlog (BlogBean blog) {
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
}
