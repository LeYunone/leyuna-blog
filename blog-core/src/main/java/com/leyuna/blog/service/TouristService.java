//package com.leyuna.blog.service;
//
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.leyuna.blog.constant.code.ServerCode;
//import com.leyuna.blog.constant.enums.SystemErrorEnum;
//import com.leyuna.blog.constant.enums.UserErrorEnum;
//import com.leyuna.blog.dao.BlogDao;
//import com.leyuna.blog.dao.CommentDao;
//import com.leyuna.blog.dao.TouristHeadDao;
//import com.leyuna.blog.dao.repository.entry.BlogDO;
//import com.leyuna.blog.dao.repository.entry.TouristHeadDO;
//import com.leyuna.blog.model.co.BlogCO;
//import com.leyuna.blog.model.co.CommentCO;
//import com.leyuna.blog.model.co.TouristHeadCO;
//import com.leyuna.blog.model.constant.DataResponse;
//import com.leyuna.blog.model.dto.CommentDTO;
//import com.leyuna.blog.util.AssertUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
///**
// * @author pengli
// * @create 2021-09-15 14:38
// * select * from comment where fatherId =  (select id from comment where blogid = ? and fatherId is null)
// * <p>
// * 游客操作领域
// */
//@Service
//public class TouristService {
//
//    @Autowired
//    private TouristHeadDao touristHeadDao;
//    @Autowired
//    private CommentDao commentDao;
//    @Autowired
//    private BlogDao blogDao;
//
//    /**
//     * 评论== 添加
//     *
//     * @return
//     */
//    public DataResponse comment(CommentDTO commentDTO) {
//
//        BlogDO blogDO = blogDao.selectById(commentDTO.getBlogId());
//        AssertUtil.isFalse(ObjectUtil.isNull(blogDO),"操作失败：文章信息不同步");
//
//        //初始化基本信息
//        if (StrUtil.isBlank(commentDTO.getInformation())) {
//            commentDTO.setInformation("不愿透露位置的某人");
//        }
//        if ("a3201360".equals(commentDTO.getInformation())) {
//            commentDTO.setInformation("365627310@qq.com");
//            commentDTO.setName("乐云一");
//            commentDTO.setCommentHead(ServerCode.SERVER_HEAD_IMG_ADMIN);
//            commentDTO.setAdmin("admin");
//        } else {
//            //自定义用户
//            TouristHeadDO touristHeadDO = touristHeadDao.selectById(commentDTO.getIp());
//            if (ObjectUtil.isNull(touristHeadDO)) {
//                commentDTO.setCommentHead(ServerCode.SERVER_HEAD_IMG_DEFAULT);
//            } else {
//                commentDTO.setCommentHead(touristHeadDO.getHead());
//            }
//        }
//        commentDTO.setGoods(0);
//        commentDao.insertOrUpdate(commentDTO);
//        blogDO.setCommentCount(blogDO.getCommentCount()+1);
//        blogDao.insertOrUpdate(blogDO);
//
//        return DataResponse.of(commentDTO);
//    }
//
//    /**
//     * 取评论
//     *
//     * @return
//     */
//    public void getComment(CommentDTO commentDTO) {
//
//    }
//
//    /**
//     * 游客进行文件上传操作： 只能是头像！ 后续待扩展
//     *
//     * @param file
//     * @param remoteAddr
//     * @return
//     */
//    public DataResponse touristDoUpImg(MultipartFile file, String remoteAddr) {
//        if (null == file) {
//            //如果文件为空，三种情况，一是使用今天换头像的缓存，二是用以前的照片，三是使用系统默认的照片
//            if (cacheExe.hasCacheByKey(remoteAddr + ":head")) {
//                String cacheByKey = cacheExe.getCacheByKey(remoteAddr + ":head");
//                return DataResponse.of(cacheByKey);
//            } else {
//                return fileExe.getTouristHead(remoteAddr);
//            }
//        } else {
//            //拼装图片位置
//            String value = ServerCode.SERVER_HEAD_IMG_ADDR + file.getOriginalFilename();
//
//            //加入今天的缓存中
//            cacheExe.setCacheKey(remoteAddr + ":head", value, 43200);
//            return fileExe.uploadHeadImg(file, value, remoteAddr);
//        }
//    }
//
//    /**
//     * 用户请求上传文件
//     *
//     * @param remoteAddr
//     * @return
//     */
//    public DataResponse requestUpImg(String remoteAddr) {
//        if (cacheExe.hasCacheByKey(remoteAddr + ":head")) {
//            //去找今天这个用户设置的头像
//            String cacheByKey = cacheExe.getCacheByKey(remoteAddr + ":head");
//            return DataResponse.buildFailure(cacheByKey);
//        }
//        return DataResponse.buildSuccess();
//    }
//
//    /**
//     * 点赞
//     *
//     * @return
//     */
//    public DataResponse addGoods(String commentId, String ip) {
//        if (cacheExe.hasCacheByKey(ip + ":" + commentId)) {
//            return DataResponse.buildFailure(UserErrorEnum.GOODS_COMMENT_FAIL.getMsg());
//        } else {
//            boolean b = commentExe.updateGoods(commentId);
//            AssertUtil.isTrue(b, SystemErrorEnum.REQUEST_FAIL.getMsg());
//            cacheExe.setCacheKey(ip + ":" + commentId, "yes", 86400);
//        }
//        return DataResponse.buildSuccess();
//    }
//}
