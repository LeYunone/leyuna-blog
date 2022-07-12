package com.leyuna.blog.core.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.core.dao.repository.entry.BlogDO;
import com.leyuna.blog.core.model.co.BlogCO;
import com.leyuna.blog.core.model.dto.BlogDTO;

import java.util.List;


/**
 * (BlogDO)dao
 *
 * @author pengli
 * @since 2021-08-13 15:38:36
 */
public interface BlogDao extends BaseDao<BlogDO> {

    Page<BlogCO> queryBlog (BlogDTO blogDTO);

    List<BlogCO> selectRandomList();
}

