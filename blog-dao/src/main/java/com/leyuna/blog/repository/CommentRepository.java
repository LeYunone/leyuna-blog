package com.leyuna.blog.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.CommentCO;
import com.leyuna.blog.entry.Comment;
import com.leyuna.blog.gateway.CommentGateway;
import com.leyuna.blog.repository.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Comment)原子服务
 *
 * @author
 * @since 2021-09-14 19:29:57
 */
@Service
public class CommentRepository extends BaseRepository<CommentMapper, Comment, CommentCO> implements CommentGateway {

    /**
     * 查询博客最新评论
     * @param index
     * @param size
     * @param blogId
     * @return
     */
    public IPage<Comment> selectNewCommentByBlogId(Integer index, Integer size, Integer blogId){
        Page<Comment> page=new Page<>(index,size);
        IPage<Comment> page1 = this.page(page, new QueryWrapper<Comment>().lambda().
                isNull(Comment::getFatherCommentId).eq(Comment::getBlogId, blogId).orderByDesc(Comment::getCreateTime));
        return page1;
    }

    /**
     * 默认查询，  根据点赞数和评论时间
     * @param index
     * @param size
     * @param blogId
     * @return
     */
    public IPage<Comment> selectNewAndGoodsCommentByBlogId(Integer index, Integer size, Integer blogId){
        Page<Comment> page=new Page<>(index,size);
        IPage<Comment> page1 = this.page(page, new QueryWrapper<Comment>().lambda().
                isNull(Comment::getFatherCommentId).eq(Comment::getBlogId, blogId).orderByDesc(Comment::getGoods,Comment::getCreateTime));
        return page1;
    }

    /**
     * 查询子评论
     * @param commentId
     * @return
     */
    public List<Comment> selectSubComment(Integer commentId){
        List<Comment> comments = this.baseMapper.selectList(new QueryWrapper<Comment>().lambda()
                .eq(Comment::getFatherCommentId, commentId).orderByDesc(Comment::getGoods, Comment::getCreateTime));
        return comments;
    }

    public boolean updateGoodsById(Integer commentId,Integer count){
        return this.update(new UpdateWrapper<Comment>().lambda().eq(Comment::getId,commentId).set(Comment::getGoods,count));
    }
}

