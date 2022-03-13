package com.leyuna.blog.control;

import com.leyuna.blog.bean.blog.CommentBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.error.UserErrorEnum;
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
    private TouristService touristDomain;
    @Autowired
    private CacheExe cacheExe;

    /**
     * 用户评论
     * @return
     */
    @PostMapping("/commpent")
    public DataResponse commpent(@RequestBody CommentBean commentBean, HttpServletRequest request){
        //设置ip
        commentBean.setIp(ServerUtil.getClientIp(request));
        return touristDomain.comment(commentBean);
    }

    /**
     * 查询指定博客下的评论  分页
     * @param index
     * @param size
     * @param blogId
     * @return
     */
    @RequestMapping("/comment/blog")
    public DataResponse getComment(CommentBean commentBean){
        return touristDomain.getComment(commentBean);
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
        boolean b = touristDomain.addGoods(commentId, ServerUtil.getClientIp(request));
        if(b) {
            return DataResponse.buildSuccess();
        }
        return DataResponse.buildFailure(UserErrorEnum.GOODS_COMMENT_FAIL.getMsg());
    }
}
