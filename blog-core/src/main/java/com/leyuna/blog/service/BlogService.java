package com.leyuna.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.dao.BlogDao;
import com.leyuna.blog.dao.MenuDao;
import com.leyuna.blog.dao.repository.entry.MenuDO;
import com.leyuna.blog.model.co.BlogCO;
import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private MenuDao menuDao;


    /**
     * 分页取得博客
     *
     * @return
     */
    public Page<BlogCO> getBlogsByPage(BlogDTO blogDTO) {
        Page<BlogCO> blogCOPage = blogDao.queryBlog(blogDTO);
        return blogCOPage;
    }

    /**
     * 发布博客
     *
     * @return
     */
    @Transactional
    @CacheEvict(cacheNames = {"blog:blogs", "blog:type", "blog:tag"}, allEntries = true)
    public void addBlog(BlogDTO blog) {
        //发布文章主题内容
        boolean addBlog = blogDao.insertOrUpdate(blog);

        //创建该文章的菜单索引
        MenuDO menuDO = new MenuDO();
        menuDO.setMenuName(blog.getTitle());
        Integer blogType = blog.getBlogType();
        menuDO.setMenuParentId(blogType);
        boolean addMenu = menuDao.insertOrUpdate(menuDO);
        AssertUtil.isTrue(addBlog || addMenu);

    }

    /**
     * 打开文章
     *
     * @param id
     * @return
     */
    public BlogCO getBlogById(String id) {
        BlogCO blogCO = blogDao.selectById(id, BlogCO.class);
        return blogCO;
    }

    /**
     * 更新文章
     *
     * @return
     */
    public void updateBlog(BlogDTO blog) {
        Integer blogType = blog.getBlogType();
        blogDao.insertOrUpdate(blogType);
    }

    /**
     * 获取刷题日记  -  leetcode题  随机  爬虫
     *
     * @return
     */
    public List<BlogCO> getLeetCode() {
        //随机获取刷题日记
        List<BlogCO> randomLeetCodeLog = blogDao.selectRandomList();
        return randomLeetCodeLog;
    }
}
