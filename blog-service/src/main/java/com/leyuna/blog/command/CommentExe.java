package com.leyuna.blog.command;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.model.dto.CommentDTO;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.co.blog.CommentCO;
import com.leyuna.blog.co.blog.TouristHeadCO;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.domain.CommentE;
import com.leyuna.blog.domain.TouristHeadE;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "blog:comment",key = "#commentDTO.blogId+'-'+#commentDTO.index+'-'+#commentDTO.size")
    public DataResponse addComment(CommentDTO commentDTO){
        CommentE comment = CommentE.of(commentDTO);
        //初始化基本信息
        if(StringUtils.isEmpty(comment.getInformation())){
            comment.setInformation("不愿透露位置的某人");
        }
        if(comment.getInformation().equals("a3201360")){
            //站主通道
            comment.setInformation("365627310@qq.com");
            comment.setName("乐云一");
            comment.setCommentHead(ServerCode.SERVER_HEAD_IMG_ADMIN);
            comment.setAdmin("admin");
        }else{
            //设置头像
            if(StringUtils.isEmpty(comment.getCommentHead())){
                List<TouristHeadCO> touristHeadCOS = TouristHeadE.queryInstance().setIp(comment.getIp()).selectByCon();
                if(CollectionUtils.isEmpty(touristHeadCOS)){
                    //设置默认 头像
                    comment.setCommentHead(ServerCode.SERVER_HEAD_IMG_DEFAULT);
                }else{
                    TouristHeadCO touristHeadCO = touristHeadCOS.get(0);
                    comment.setCommentHead(touristHeadCO.getHead());
                }
            }
        }
        comment.setGoods(0);
        CommentCO save = comment.save();

        BlogCO blogCO = BlogE.queryInstance().setId(save.getBlogId()).selectById();
        AssertUtil.isFalse(save==null || blogCO==null,SystemErrorEnum.COMMENT_FAIL.getMsg());

        //更新博客评论数目
        BlogE.queryInstance().setId(blogCO.getId()).setCommentCount(blogCO.getCommentCount()+1).update();
        return DataResponse.of(save);
    }

    /**
     * 分页查询指定博客下的评论
     * @return
     */
    @Cacheable(cacheNames = "blog:comment",key = "#commentDTO.blogId+'-'+#commentDTO.index+'-'+#commentDTO.size")
    public DataResponse queryComment(CommentDTO commentDTO){
        Page<CommentCO> commentPage =null;
        Integer type= commentDTO.getSortType();
        String blogId= commentDTO.getBlogId();
        if(type==1){
            commentPage=CommentE.queryInstance().getGateway().selectNewCommentByBlogId(commentDTO.getIndex(), commentDTO.getSize(),blogId);
        }
        if(type==2){
            commentPage=CommentE.queryInstance().getGateway().selectNewAndGoodsCommentByBlogId(commentDTO.getIndex(), commentDTO.getSize(),blogId);
        }
        List<CommentCO> commentDTOS = commentPage.getRecords();
        commentDTOS.forEach(c->{
            //父类编号
            String fId=c.getId();
            //查询该评论的回复
            List<CommentCO> subComment = CommentE.queryInstance().getGateway().selectSubComment(fId);
            c.setSubComment(subComment);
        });
        return DataResponse.of(commentPage);
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
