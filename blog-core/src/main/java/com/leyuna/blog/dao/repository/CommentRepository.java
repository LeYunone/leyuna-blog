package com.leyuna.blog.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.dao.CommentDao;
import com.leyuna.blog.dao.repository.entry.CommentDO;
import com.leyuna.blog.dao.repository.mapper.CommentMapper;
import com.leyuna.blog.model.co.CommentCO;
import com.leyuna.blog.util.TransformationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Comment)原子服务
 *
 * @author
 * @since 2021-09-14 19:29:57
 */
@Service
public class CommentRepository extends BaseRepository<CommentMapper, CommentDO> implements CommentDao {

    /**
     * 定制查询
     * @param index
     * @param size
     * @param blogId
     * @return
     */
    public IPage<CommentDO> selectNewCommentByBlogId(Integer index, Integer size, String blogId){
        Page<CommentDO> page=new Page<>(index,size);
        IPage<CommentDO> page1 = this.page(page, new QueryWrapper<CommentDO>().lambda().
                isNull(CommentDO::getFatherCommentId).eq(CommentDO::getBlogId, blogId).orderByDesc(CommentDO::getCreateDt));
        return page1;
    }

    /**
     * 默认查询，  根据点赞数和评论时间
     * @param index
     * @param size
     * @param blogId
     * @return
     */
    public Page<CommentCO> selectNewAndGoodsCommentByBlogId(Integer index, Integer size, String blogId){
        Page<CommentDO> page=new Page<>(index,size);
        IPage<CommentDO> page1 = this.page(page, new QueryWrapper<CommentDO>().lambda().
                isNull(CommentDO::getFatherCommentId).eq(CommentDO::getBlogId, blogId)
                .orderByDesc(CommentDO::getGoods,CommentDO::getCreateDt));
        return TransformationUtil.copyToPage(page1,CommentCO.class);
    }

    /**
     * 查询子评论
     * @param commentId
     * @return
     */
    public List<CommentCO> selectSubComment(String commentId){
        List<CommentDO> comments = this.baseMapper.selectList(new QueryWrapper<CommentDO>().lambda()
                .eq(CommentDO::getFatherCommentId, commentId)
                .orderByDesc(CommentDO::getGoods, CommentDO::getCreateDt));
        return TransformationUtil.copyToLists(comments,CommentCO.class);
    }

    public boolean updateGoodsById(String commentId,Integer count){
        return this.update(new UpdateWrapper<CommentDO>().lambda().eq(CommentDO::getId,commentId).set(CommentDO::getGoods,count));
    }
}

