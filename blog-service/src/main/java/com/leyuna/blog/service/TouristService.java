package com.leyuna.blog.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyuna.blog.bean.blog.CommentBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.command.CommentExe;
import com.leyuna.blog.command.FileExe;
import com.leyuna.blog.error.SystemErrorEnum;
import com.leyuna.blog.error.UserErrorEnum;
import com.leyuna.blog.util.AssertUtil;
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
public class TouristService {

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
    public DataResponse comment(CommentBean commentDTO){
        //添加评论
        return commentExe.addComment(commentDTO);
    }

    /**
     * 取评论
     * @return
     */
    public DataResponse getComment(CommentBean commentBean){
        return commentExe.queryComment(commentBean);
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
    public DataResponse addGoods(String commentId ,String ip){
        if(cacheExe.hasCacheByKey(ip+":"+commentId)){
            return DataResponse.buildFailure(UserErrorEnum.GOODS_COMMENT_FAIL.getMsg());
        }else{
            boolean b = commentExe.updateGoods(commentId);
            AssertUtil.isTrue(b, SystemErrorEnum.REQUEST_FAIL.getMsg());
            cacheExe.setCacheKey(ip+":"+commentId,"yes",86400);
        }
        return DataResponse.buildSuccess();
    }
}
