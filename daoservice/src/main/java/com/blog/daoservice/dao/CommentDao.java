package com.blog.daoservice.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.daoservice.entry.Comment;


import java.io.Serializable;
import java.util.List;

/**
 * (Comment)dao
 *
 * @author
 * @since 2021-09-14 19:29:57
 */
public interface CommentDao extends SysBaseDao<Comment> {

    IPage<Comment> selectNewCommentByBlogId(Integer index, Integer size, Integer blogId);

    IPage<Comment> selectNewAndGoodsCommentByBlogId(Integer index, Integer size, Integer blogId);

    List<Comment> selectSubComment(Integer commentId);

    boolean updateGoodsById(Integer commentId,Integer count);
}

