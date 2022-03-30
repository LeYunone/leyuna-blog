package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.co.blog.BlogCO;

import java.util.List;

/**
 * (BlogDO)dao
 *
 * @author pengli
 * @since 2021-08-13 15:38:36
 */
public interface BlogGateway extends BaseGateway<BlogCO> {

    Page<BlogCO> queryBlog (BlogBean blogBean);

    List<BlogCO> selectRandomList();
}

