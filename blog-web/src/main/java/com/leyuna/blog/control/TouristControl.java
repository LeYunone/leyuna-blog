package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.CommentBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.CommentCO;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.constant.ServerCode;
import com.leyuna.blog.domain.CommentE;
import com.leyuna.blog.error.SystemAsserts;
import com.leyuna.blog.error.UserAsserts;
import com.leyuna.blog.service.TouristService;
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
    private TouristService TouristService;
    @Autowired
    private CacheExe cacheExe;

    /**
     * 用户评论
     * @return
     */
    @PostMapping("/commpent")
    public DataResponse commpent(@RequestBody CommentBean commentBean, HttpServletRequest request){
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
                String touristOldHead = TouristService.getTouristOldHead(remoteAddr);
                if(!StringUtils.isEmpty(touristOldHead)){
                    commentDTO.setCommentHead(touristOldHead);
                }else{
                    commentDTO.setCommentHead(ServerCode.SERVER_HEAD_IMG_DEFAULT);
                }
            }
        }
        CommentCO comment = TouristService.comment(commentDTO);
        if(comment!=null){
            return DataResponse.of(comment);
        }
        return DataResponse.buildFailure(SystemAsserts.COMMENT_FAIL.getMsg());
    }

    /**
     * 查询指定博客下的评论  分页
     * @param index
     * @param size
     * @param blogId
     * @return
     */
    @RequestMapping("/comment/blog")
    public DataResponse getComment(Integer index,Integer size,String blogId,@RequestParam(required = false) Integer type){
        Page<CommentCO> comment = TouristService.getComment(index, size, blogId, type);
        return DataResponse.of(comment);
    }

    /**
     * 用户请求上传头像图片
     */
    @RequestMapping("/requestUpImg")
    public DataResponse requestUpImg(HttpServletRequest request){
        String remoteAddr = ServerUtil.getClientIp(request);
        boolean b = cacheExe.hasCacheByKey(remoteAddr+":head");
        if(b){
            //去找今天这个用户设置的头像
            String cacheByKey = cacheExe.getCacheByKey(remoteAddr + ":head");
            return DataResponse.buildFailure(cacheByKey);
        }else{
            return DataResponse.buildSuccess();
        }
    }

    @RequestMapping("/goods")
    public DataResponse goodsByComment(String commentId,HttpServletRequest request){
        boolean b = TouristService.addGoods(commentId, ServerUtil.getClientIp(request));
        if(b) {
            return DataResponse.buildSuccess();
        }
        return DataResponse.buildFailure(UserAsserts.GOODS_COMMENT_FAIL.getMsg());
    }
}
