package com.leyuna.blog.dao;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.dao.repository.entry.CommentDO;
import com.leyuna.blog.model.co.CommentCO;

import java.util.List;


/**
 * (Comment)dao
 *
 * @author
 * @since 2021-09-14 19:29:57
 */
public interface CommentDao extends BaseDao<CommentDO> {

    IPage<CommentDO> selectNewCommentByBlogId (Integer index, Integer size, String blogId);

    Page<CommentCO> selectNewAndGoodsCommentByBlogId (Integer index, Integer size, String blogId);

    List<CommentCO> selectSubComment (String commentId);

    boolean updateGoodsById (String commentId, Integer count);
}

