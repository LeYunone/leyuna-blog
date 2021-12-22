package com.leyuna.blog.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.command.CacheExe;
import com.blog.api.command.CommentExe;
import com.blog.api.command.FileExe;
import com.blog.api.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private CacheExe cacheExe;
    @Autowired
    private FileExe fileExe;
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
     * 取评论
     * @param index
     * @param size
     * @param blogId
     * @param type  [类型： 最新   最热  混杂型]
     * @return
     */
    public Page<CommentDTO> getComment(Integer index,Integer size,Integer blogId,Integer type){
        Page<CommentDTO> commentDTOPage=null;
        commentDTOPage=commentExe.queryComment(index, size, blogId, type);
        return commentDTOPage;
    }

    public String getTouristOldHead(String ip){
        String touristHead = fileExe.getTouristHead(ip);
        return touristHead;
    }

    public boolean addOrUpdateHead(String head,String ip){
        String touristHead = fileExe.getTouristHead(ip);
        boolean is=true;
        if(StringUtils.isNotEmpty(touristHead)){
            //说明需要更新头像
            is=fileExe.updateTouristHead(head, ip);
        }else{
            is=fileExe.addTouristHead(head,ip);
        }
        return is;
    }

    /**
     * 点赞
     * @return
     */
    public boolean addGoods(Integer commentId,String ip){
        if(cacheExe.hasCacheByKey(ip+":"+commentId)){
            return false;
        }else{
            boolean b = commentExe.updateGoods(commentId);
            if(b){
                cacheExe.setCacheKey(ip+":"+commentId,"yes",86400);
            }
            return b;
        }
    }
}
