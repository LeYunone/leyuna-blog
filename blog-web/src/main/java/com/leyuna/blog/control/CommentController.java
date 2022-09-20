package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.model.co.CommentCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.model.dto.CommentDTO;
import com.leyuna.blog.model.query.CommentQuery;
import com.leyuna.blog.service.CommentService;
import com.leyuna.blog.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-28
 */
@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    /**
     * 用户评论
     * @return
     */
    @PostMapping(value = "/add")
    public DataResponse addComment(@RequestBody CommentDTO commentDTO){
        CommentDTO comment = commentService.comment(commentDTO);
        return DataResponse.of(comment);
    }


    /**
     * 查询指定博客下的评论  分页
     * @return
     */
    @RequestMapping("/blog")
    public DataResponse getComment(CommentQuery commentQuery){
        Page<CommentCO> commentPage = commentService.getCommentPage(commentQuery);
        return DataResponse.of(commentPage);
    }
}
