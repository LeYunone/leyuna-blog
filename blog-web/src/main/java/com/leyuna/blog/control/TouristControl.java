package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.model.co.CommentCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.model.dto.CommentDTO;
import com.leyuna.blog.model.query.CommentQuery;
import com.leyuna.blog.service.CommentService;
import com.leyuna.blog.service.ImageService;
import com.leyuna.blog.util.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pengli
 * @create 2021-09-14 16:57
 *
 * 游客操作 控制器   需要限制取ip限制他们的操作频率
 */
@RequestMapping("/tourist")
@RestController
public class TouristControl {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    /**
     * 用户评论
     * @return
     */
    @PostMapping("/comment")
    public DataResponse comment(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        //设置ip
        commentDTO.setIp(ServerUtil.getClientIp(request));
        CommentDTO comment = commentService.comment(commentDTO);
        return DataResponse.of(comment);
    }

    /**
     * 查询指定博客下的评论  分页
     * @return
     */
    @RequestMapping("/comment/blog")
    public DataResponse getComment(CommentQuery commentQuery){
        Page<CommentCO> commentPage = commentService.getCommentPage(commentQuery);
        return DataResponse.of(commentPage);
    }

    /**
     * 用户请求上传头像图片
     */
    @RequestMapping("/requestUpImg")
    public DataResponse requestUpImg(HttpServletRequest request){
        String remoteAddr = ServerUtil.getClientIp(request);
        return imageService.requestUpImg(remoteAddr);
    }

    /**
     * 评论点赞
     * @param commentId
     * @param request
     * @return
     */
    @RequestMapping("/goods")
    public DataResponse goodsByComment(String commentId,HttpServletRequest request){
        return commentService.addGoods(commentId, ServerUtil.getClientIp(request));
    }
}
