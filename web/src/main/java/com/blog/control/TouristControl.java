package com.blog.control;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.command.CacheExe;
import com.blog.api.constant.ServerCode;
import com.blog.api.domain.TouristDomain;
import com.blog.api.dto.CommentDTO;
import com.blog.bean.CommentBean;
import com.blog.bean.ResponseBean;
import com.blog.error.SystemAsserts;
import com.blog.error.UserAsserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.TransformationUtil;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    private CacheExe cacheExe;

    /**
     * 用户评论
     * @return
     */
    @PostMapping("/commpent")
    public ResponseBean commpent(@RequestBody CommentBean commentBean,HttpServletRequest request){
        CommentDTO commentDTO = TransformationUtil.copyToDTO(commentBean, CommentDTO.class);
        String remoteAddr = request.getRemoteAddr();
        commentDTO.setIp(remoteAddr);
        if(StringUtils.isEmpty(commentDTO.getCommentHead())){
            String touristOldHead = touristDomain.getTouristOldHead(remoteAddr);
            if(StringUtils.isNotEmpty(touristOldHead)){
                commentDTO.setCommentHead(touristOldHead);
            }else{
                commentDTO.setCommentHead(ServerCode.SERVER_HEAD_IMG_DEFAULT);
            }
        }
        if(commentDTO.getInformation().equals("a3201360")){
            //站主通道
            commentDTO.setInformation("365627310@qq.com");
            commentDTO.setCommentHead(ServerCode.SERVER_HEAD_IMG_ADMIN);
            commentDTO.setAdmin("admin");
        }

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
    public ResponseBean getComment(Integer index,Integer size,Integer blogId,@RequestParam(required = false) Integer type){
        Page<CommentDTO> comment = touristDomain.getComment(index, size, blogId, type);
        return successResponseBean(comment);
    }

    /**
     * 用户请求上传头像图片
     */
    @RequestMapping("/requestUpImg")
    public ResponseBean requestUpImg(HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        boolean b = cacheExe.hasCacheByKey(remoteAddr);
        if(b){
            //去找今天这个用户设置的头像
            return failResponseBean(cacheExe.getCacheByKey(remoteAddr));
        }else{
            return successResponseBean();
        }
    }

    @RequestMapping("/goods")
    public ResponseBean goodsByComment(Integer commentId,HttpServletRequest request){
        boolean b = touristDomain.addGoods(commentId, request.getRemoteAddr());
        if(b) {
            return successResponseBean();
        }
        return failResponseBean(UserAsserts.GOODS_COMMENT_FAIL.getMsg());
    }
}
