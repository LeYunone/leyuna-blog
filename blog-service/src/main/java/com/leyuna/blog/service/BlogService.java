package com.leyuna.blog.service;

import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.NoticeBean;
import com.leyuna.blog.command.BlogExe;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.command.LuceneExe;
import com.leyuna.blog.command.NoticeExe;
import com.leyuna.blog.error.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private NoticeExe noticeExe;
    @Autowired
    private CacheExe clearCacheExe;
    @Autowired
    private LuceneExe luceneExe;

    /**
     * 分页取得博客   根据分类或者标签或者查所有
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
    public DataResponse addBlog (BlogBean blogDTO) {
        //添加博客
        blogExe.addBlog(blogDTO);

        //添加改博客的索引库文档
        List<BlogBean> list = new ArrayList<>();
        list.add(blogDTO);
        luceneExe.addBlogDir(list);
        //更新博客\标签\分类\分类缓存
        clearCacheExe.clearBlogQueryCache();
        clearCacheExe.clearTagQueryCache();
        clearCacheExe.clearTypeQueryCache();
        clearCacheExe.clearTypeNavQueryCache();
        return DataResponse.buildSuccess();
    }

    /**
     * 打开文章
     *
     * @param id
     * @return
     */
    @Transactional
    public DataResponse openBlogById (String id) {
        return blogExe.getBlog(id, 1);
    }

    /**
     * 更新文章
     *
     * @param blogDTO
     * @return
     */
    public DataResponse updateBlog (BlogBean blogDTO) {
        boolean update = blogExe.updateBlog(blogDTO);
        AssertUtil.isTrue(update, SystemErrorEnum.UPDATE_BLOG_FAIL.getMsg());
        //更新索引库中的Key
        luceneExe.updateBlogDocument(blogDTO);
        //清除这篇文章的缓存
        clearCacheExe.clearBlogQueryByIdCache(blogDTO.getId());
        clearCacheExe.clearBlogQueryCache();
        return DataResponse.buildSuccess();
    }

    /**
     * 添加各类版本的公示 方法
     *
     * @param noticeDTO
     * @return
     */
    public DataResponse addNotice (NoticeBean noticeDTO) {

        Integer type = noticeDTO.getType();
        switch (type) {
            case 0:
                //添加历史版本公告
                noticeExe.addHistory(noticeDTO);
        }
        //清空缓存
        clearCacheExe.clearWebHistoryCache();
        return DataResponse.buildSuccess();
    }

    /**
     * 分页获取公告
     *
     * @param index
     * @param size
     * @param conditionName
     * @param type
     * @return
     */
    public DataResponse getNoticePage (Integer index, Integer size, String conditionName, int type) {
        switch (type) {
            case 0:
                //查询网站更新公告
                return noticeExe.getWebHistory(index, size, conditionName);
            default:
                return null;
        }
    }

    /**
     * 得到一条公告
     *
     * @param id
     * @param type
     * @return
     */
    public DataResponse getNoticeOne (String id, int type) {
        switch (type) {
            case 0:
                return noticeExe.getWebHistoryById(id);
            default:
                return null;
        }
    }

    /**
     * 更新公告
     *
     * @param noticeDTO
     * @return
     */
    public DataResponse updateNotice (NoticeBean noticeDTO) {
        switch (noticeDTO.getType()) {
            case 0:
                noticeExe.updateWebHis(noticeDTO);
            default:
        }
        clearCacheExe.clearWebHistoryCache();
        return DataResponse.buildSuccess();
    }
}
