package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leyuna.blog.co.CommentCO;

import java.util.List;

/**
 * (Comment)dao
 *
 * @author
 * @since 2021-09-14 19:29:57
 */
public interface CommentGateway extends BaseGateway<CommentCO> {

    IPage<CommentCO> selectNewCommentByBlogId (Integer index, Integer size, String blogId);

    IPage<CommentCO> selectNewAndGoodsCommentByBlogId (Integer index, Integer size, String blogId);

    List<CommentCO> selectSubComment (String commentId);

    boolean updateGoodsById (String commentId, Integer count);
}

