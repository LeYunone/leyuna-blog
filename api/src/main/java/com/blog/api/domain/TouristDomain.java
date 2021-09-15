package com.blog.api.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.command.CommentExe;
import com.blog.api.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengli
 * @create 2021-09-15 14:38
 *  select * from comment where fatherId =  (select id from comment where blogid = ? and fatherId is null)
 *
 * 游客操作领域
 */
@Service
public class TouristDomain {

    @Autowired
    private CommentExe commentExe;
    /**
     * 评论== 添加
     * @return
     */
    public CommentDTO comment(CommentDTO commentDTO){
        //添加评论
        CommentDTO thisComment=commentExe.addComment(commentDTO);
        if(thisComment!=null){
            //如果添加评论成功了，则需要回显这次评论到页面上
            //待扩展 9/15
            return thisComment;
        }
        return null;
    }

    /**
     * 取指定博客下的所有评论
     * @param index
     * @param size
     * @param blogId
     * @param type  [类型： 最新   最热  混杂型]
     * @return
     */
    public Page<CommentDTO> getBlogComment(Integer index,Integer size,Integer blogId,Integer type){
        Page<CommentDTO> commentDTOPage=null;
        switch (type){
            case 0:
                //0为最新评论
                commentDTOPage = commentExe.queryComment(index, size, blogId);
                break;
            case 1:
                //1 根据时间和热度一起排序

                break;
            default:
                break;
        }
        return commentDTOPage;
    }
}
