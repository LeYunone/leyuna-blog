package com.leyuna.blog.command;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.CommentCO;
import com.leyuna.blog.domain.CommentE;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author pengli
 * @create 2021-09-15 14:48
    评论操作指令
 */
@Service
public class CommentExe {

    /**
     * 添加评论
     * @return
     */
    public CommentCO addComment(CommentE commentDTO){
        if(StringUtils.isEmpty(commentDTO.getInformation())) commentDTO.setInformation("不愿透露位置的某人");
        commentDTO.setCreateTime(LocalDateTime.now());
        commentDTO.setGoods(0);
        CommentCO save = commentDTO.save();
        return save;
    }

    /**
     * 分页查询指定博客下的评论
     * @return
     */
    public Page<CommentCO> queryComment(Integer index , Integer size, String blogId, Integer type){
        Page<CommentCO> commentPage =null;
        if(type==0){
            commentPage=CommentE.queryInstance().getGateway().selectNewCommentByBlogId(index,size,blogId);
        }
        if(type==1){
            commentPage=CommentE.queryInstance().getGateway().selectNewAndGoodsCommentByBlogId(index,size,blogId);
        }
        List<CommentCO> commentDTOS = commentPage.getRecords();
        commentDTOS.forEach(c->{
            //父类编号
            String fId=c.getId();
            //查询该评论的回复
            List<CommentCO> subComment = CommentE.queryInstance().getGateway().selectSubComment(fId);
            c.setSubComment(subComment);
        });
        return commentPage;
    }

    /**
     * 点赞加一
     * @return
     */
    public boolean updateGoods(String commentId){
        CommentCO byId = CommentE.queryInstance().setId(commentId).selectById();
        Integer goods = byId.getGoods();
        boolean b = CommentE.queryInstance().getGateway().updateGoodsById(commentId, goods + 1);
        return b;
    }
}
