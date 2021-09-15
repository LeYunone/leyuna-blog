package com.blog.control;

import com.blog.api.domain.TouristDomain;
import com.blog.api.dto.CommentDTO;
import com.blog.bean.CommentBean;
import com.blog.bean.ResponseBean;
import com.blog.error.SystemAsserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import util.TransformationUtil;

/**
 * @author pengli
 * @create 2021-09-14 16:57
 *
 * 游客操作 控制器   需要限制取ip限制他们的操作频率
 */
@RequestMapping("/tourist")
@RestController
public class TouristControl extends SysBaseControl{

    @Autowired
    private TouristDomain touristDomain;

    /**
     * 用户评论
     * @return
     */
    @RequestMapping("/commpent")
    public ResponseBean commpent(@RequestBody CommentBean commentBean){
        CommentDTO commentDTO = TransformationUtil.copyToDTO(commentBean, CommentDTO.class);
        CommentDTO comment = touristDomain.comment(commentDTO);
        if(comment!=null){
            return successResponseBean(comment);
        }
        return failResponseBean(SystemAsserts.COMMENT_FAIL.getMsg());
    }

    /**
     * 查询指定博客下的评论  分页
     * @param index
     * @param size
     * @param blogId
     * @return
     */
    @RequestMapping("/comment/blog")
    public ResponseBean getComment(Integer index,Integer size,Integer blogId){

    }
}
