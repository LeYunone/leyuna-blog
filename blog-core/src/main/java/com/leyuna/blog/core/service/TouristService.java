package com.leyuna.blog.core.service;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.command.CommentExe;
import com.leyuna.blog.command.FileExe;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.constant.enums.UserErrorEnum;
import com.leyuna.blog.model.dto.CommentDTO;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pengli
 * @create 2021-09-15 14:38
 * select * from comment where fatherId =  (select id from comment where blogid = ? and fatherId is null)
 * <p>
 * 游客操作领域
 */
@Service
public class TouristService {

    @Autowired
    private CommentExe commentExe;
    @Autowired
    private CacheExe cacheExe;
    @Autowired
    private FileExe fileExe;

    /**
     * 评论== 添加
     *
     * @return
     */
    public DataResponse comment(CommentDTO commentDTO) {
        //添加评论
        return commentExe.addComment(commentDTO);
    }

    /**
     * 取评论
     *
     * @return
     */
    public DataResponse getComment(CommentDTO commentDTO) {
        return commentExe.queryComment(commentDTO);
    }

    /**
     * 游客进行文件上传操作： 只能是头像！ 后续待扩展
     * @param file
     * @param remoteAddr
     * @return
     */
    public DataResponse touristDoUpImg(MultipartFile file, String remoteAddr) {
        if (null == file) {
            //如果文件为空，三种情况，一是使用今天换头像的缓存，二是用以前的照片，三是使用系统默认的照片
            if (cacheExe.hasCacheByKey(remoteAddr + ":head")) {
                String cacheByKey = cacheExe.getCacheByKey(remoteAddr + ":head");
                return DataResponse.of(cacheByKey);
            } else {
                return fileExe.getTouristHead(remoteAddr);
            }
        } else {
            //拼装图片位置
            String value = ServerCode.SERVER_HEAD_IMG_ADDR + file.getOriginalFilename();

            //加入今天的缓存中
            cacheExe.setCacheKey(remoteAddr + ":head", value, 43200);
            return fileExe.uploadHeadImg(file, value, remoteAddr);
        }
    }

    /**
     * 用户请求上传文件
     * @param remoteAddr
     * @return
     */
    public DataResponse requestUpImg(String remoteAddr){
        if(cacheExe.hasCacheByKey(remoteAddr+":head")){
            //去找今天这个用户设置的头像
            String cacheByKey = cacheExe.getCacheByKey(remoteAddr + ":head");
            return DataResponse.buildFailure(cacheByKey);
        }
        return DataResponse.buildSuccess();
    }

    /**
     * 点赞
     *
     * @return
     */
    public DataResponse addGoods(String commentId, String ip) {
        if (cacheExe.hasCacheByKey(ip + ":" + commentId)) {
            return DataResponse.buildFailure(UserErrorEnum.GOODS_COMMENT_FAIL.getMsg());
        } else {
            boolean b = commentExe.updateGoods(commentId);
            AssertUtil.isTrue(b, SystemErrorEnum.REQUEST_FAIL.getMsg());
            cacheExe.setCacheKey(ip + ":" + commentId, "yes", 86400);
        }
        return DataResponse.buildSuccess();
    }
}
