package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.BlogCO;

/**
 * (Blog)dao
 *
 * @author pengli
 * @since 2021-08-13 15:38:36
 */
public interface BlogGateway extends BaseGateway<BlogCO> {

    Page<BlogCO> queryByTagName (BlogCO e, Integer index , Integer size);

    int queryCountByType (Integer type);

    boolean updateClickCount (String blogId, Integer clickCount);

    Page<BlogCO> queryByBlogName (String title, Integer index,Integer size);
}

