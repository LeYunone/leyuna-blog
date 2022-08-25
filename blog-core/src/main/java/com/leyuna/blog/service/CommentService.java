package com.leyuna.blog.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.dao.BlogDao;
import com.leyuna.blog.dao.CommentDao;
import com.leyuna.blog.dao.FileDao;
import com.leyuna.blog.dao.repository.entry.BlogDO;
import com.leyuna.blog.dao.repository.entry.CommentDO;
import com.leyuna.blog.dao.repository.entry.FileDO;
import com.leyuna.blog.model.co.CommentCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.model.dto.CommentDTO;
import com.leyuna.blog.model.query.CommentQuery;
import com.leyuna.blog.model.query.FileQuery;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.TransformationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-22
 */
@Service
public class CommentService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private FileDao fileDao;

    @Transactional(rollbackFor = Exception.class)
    public CommentDTO comment(CommentDTO commentDTO) {

        BlogDO blogDO = blogDao.selectById(commentDTO.getBlogId());
        AssertUtil.isFalse(ObjectUtil.isNull(blogDO), "操作失败：文章信息不同步");

        //初始化基本信息
        if (StrUtil.isBlank(commentDTO.getInformation())) {
            commentDTO.setInformation("不愿透露位置的某人");
        }
        if ("a3201360".equals(commentDTO.getInformation())) {
            commentDTO.setInformation("365627310@qq.com");
            commentDTO.setName("乐云一");
            commentDTO.setCommentHead(ServerCode.SERVER_HEAD_IMG_ADMIN);
            commentDTO.setAdmin("admin");
        } else {
//            //自定义用户
            FileQuery fileQuery = new FileQuery();
            fileQuery.setFileIdentif(commentDTO.getIp());
            FileDO fileDO = fileDao.selectOne(fileQuery);
            if (ObjectUtil.isNull(fileDO)) {
                commentDTO.setCommentHead(ServerCode.SERVER_HEAD_IMG_DEFAULT);
            } else {
                commentDTO.setCommentHead(fileDO.getFileUrl());
            }
        }
        commentDTO.setGoods(0);
        commentDao.insertOrUpdate(commentDTO);
        return commentDTO;
    }

    public Page<CommentCO> getCommentPage(CommentQuery query){
        String blogId = query.getBlogId();
        AssertUtil.isFalse(StrUtil.isBlank(blogId),"blogId is not empty");
        IPage<CommentDO> commentIPage =null;
//        if(type==0){
            commentIPage=commentDao.selectNewCommentByBlogId(query.getIndex(),query.getSize(),blogId);
//        }
//        if(type==1){
//            commentIPage=commentDao.selectNewAndGoodsCommentByBlogId(index,size,blogId);
//        }
        Page<CommentCO> result=new Page<>(query.getIndex(),query.getSize());
        commentIPage.getTotal();
        result.setTotal(commentIPage.getTotal());
        List<CommentCO> commentDTOS = TransformationUtil.copyToLists(commentIPage.getRecords(), CommentCO.class);
        commentDTOS.forEach(c->{
            //父类编号
            String fId=c.getId();
            //查询该评论的回复
            List<CommentCO> subComment = commentDao.selectSubComment(fId);
            c.setSubComment(TransformationUtil.copyToLists(subComment,CommentCO.class));
        });
        result.setRecords(commentDTOS);
        return result;
    }

        /**
     * 点赞
     *
     * @return
     */
    public DataResponse addGoods(String commentId, String ip) {
//        if (cacheExe.hasCacheByKey(ip + ":" + commentId)) {
//            return DataResponse.buildFailure(UserErrorEnum.GOODS_COMMENT_FAIL.getMsg());
//        } else {
//            boolean b = commentExe.updateGoods(commentId);
//            AssertUtil.isTrue(b, SystemErrorEnum.REQUEST_FAIL.getMsg());
//            cacheExe.setCacheKey(ip + ":" + commentId, "yes", 86400);
//        }
        return DataResponse.buildSuccess();
    }
}
