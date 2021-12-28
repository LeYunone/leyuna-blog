package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.CommentBean;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.co.blog.CommentCO;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.constant.ServerCode;
import com.leyuna.blog.domain.CommentE;
import com.leyuna.blog.error.SystemAsserts;
import com.leyuna.blog.error.UserAsserts;
import com.leyuna.blog.service.TouristDomain;
import com.leyuna.blog.util.ServerUtil;
import com.leyuna.blog.util.TransformationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private TouristDomain touristDomain;
    @Autowired
    private CacheExe cacheExe;

    /**
     * 用户评论
     * @return
     */
    @PostMapping("/commpent")
    public ResponseBean commpent(@RequestBody CommentBean commentBean, HttpServletRequest request){
        CommentE commentDTO = TransformationUtil.copyToDTO(commentBean, CommentE.class);
        String remoteAddr = ServerUtil.getClientIp(request);
        commentDTO.setIp(remoteAddr);
        if(commentDTO.getInformation().equals("a3201360")){
            //站主通道
            commentDTO.setInformation("365627310@qq.com");
            commentDTO.setCommentHead(ServerCode.SERVER_HEAD_IMG_ADMIN);
            commentDTO.setAdmin("admin");
        }else{
            if(StringUtils.isEmpty(commentDTO.getCommentHead())){
                String touristOldHead = touristDomain.getTouristOldHead(remoteAddr);
                if(!StringUtils.isEmpty(touristOldHead)){
                    commentDTO.setCommentHead(touristOldHead);
                }else{
                    commentDTO.setCommentHead(ServerCode.SERVER_HEAD_IMG_DEFAULT);
                }
            }
        }
        CommentCO comment = touristDomain.comment(commentDTO);
        if(comment!=null){
            return ResponseBean.of(comment);
        }
        return ResponseBean.buildFailure(SystemAsserts.COMMENT_FAIL.getMsg());
    }

    /**
     * 查询指定博客下的评论  分页
     * @param index
     * @param size
     * @param blogId
     * @return
     */
    @RequestMapping("/comment/blog")
    public ResponseBean getComment(Integer index,Integer size,String blogId,@RequestParam(required = false) Integer type){
        Page<CommentCO> comment = touristDomain.getComment(index, size, blogId, type);
        return ResponseBean.of(comment);
    }

    /**
     * 用户请求上传头像图片
     */
    @RequestMapping("/requestUpImg")
    public ResponseBean requestUpImg(HttpServletRequest request){
        String remoteAddr = ServerUtil.getClientIp(request);
        boolean b = cacheExe.hasCacheByKey(remoteAddr+":head");
        if(b){
            //去找今天这个用户设置的头像
            String cacheByKey = cacheExe.getCacheByKey(remoteAddr + ":head");
            return ResponseBean.buildFailure(cacheByKey);
        }else{
            return ResponseBean.buildSuccess();
        }
    }

    @RequestMapping("/goods")
    public ResponseBean goodsByComment(String commentId,HttpServletRequest request){
        boolean b = touristDomain.addGoods(commentId, ServerUtil.getClientIp(request));
        if(b) {
            return ResponseBean.buildSuccess();
        }
        return ResponseBean.buildFailure(UserAsserts.GOODS_COMMENT_FAIL.getMsg());
    }
}
