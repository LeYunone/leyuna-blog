package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leyuna.blog.co.CommentCO;
import com.leyuna.blog.entry.Comment;

import java.util.List;

/**
 * (Comment)dao
 *
 * @author
 * @since 2021-09-14 19:29:57
 */
public interface CommentGateway extends BaseGateway<CommentCO> {

    IPage<Comment> selectNewCommentByBlogId (Integer index, Integer size, Integer blogId);

    IPage<Comment> selectNewAndGoodsCommentByBlogId (Integer index, Integer size, Integer blogId);

    List<Comment> selectSubComment (Integer commentId);

    boolean updateGoodsById (Integer commentId, Integer count);
}

