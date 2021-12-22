package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.BlogCO;
import com.leyuna.blog.entry.Blog;

/**
 * (Blog)dao
 *
 * @author pengli
 * @since 2021-08-13 15:38:36
 */
public interface BlogGateway extends BaseGateway<BlogCO> {

    IPage<Blog> queryByTagName (Blog e, Page<Blog> page);

    int queryCountByType (Integer type);

    boolean updateClickCount (Integer blogId, Integer clickCount);

    IPage<Blog> queryByBlogName (String title, Page<Blog> page);
}

