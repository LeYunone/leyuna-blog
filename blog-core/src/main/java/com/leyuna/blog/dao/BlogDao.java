package com.leyuna.blog.dao;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.dao.repository.entry.BlogDO;
import com.leyuna.blog.model.co.BlogCO;
import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.model.query.BlogQuery;

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

    IPage<BlogDO> selectByMenuTopOrderTime(BlogQuery blogQuery);

    List<BlogDO> selectLeetCode();

    List<BlogDO> selectNormalNotes();
}

