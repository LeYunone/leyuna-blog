package com.leyuna.blog.control;

import com.leyuna.blog.model.dto.CommentDTO;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.service.TouristService;
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
    private TouristService touristService;

    /**
     * 用户评论
     * @return
     */
    @PostMapping("/comment")
    public DataResponse comment(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        //设置ip
        commentDTO.setIp(ServerUtil.getClientIp(request));
        return touristService.comment(commentDTO);
    }

    /**
     * 查询指定博客下的评论  分页
     * @return
     */
    @RequestMapping("/comment/blog")
    public DataResponse getComment(CommentDTO commentDTO){
        return touristService.getComment(commentDTO);
    }

    /**
     * 用户请求上传头像图片
     */
    @RequestMapping("/requestUpImg")
    public DataResponse requestUpImg(HttpServletRequest request){
        String remoteAddr = ServerUtil.getClientIp(request);
        return touristService.requestUpImg(remoteAddr);
    }

    /**
     * 评论点赞
     * @param commentId
     * @param request
     * @return
     */
    @RequestMapping("/goods")
    public DataResponse goodsByComment(String commentId,HttpServletRequest request){
        return touristService.addGoods(commentId, ServerUtil.getClientIp(request));
    }
}
