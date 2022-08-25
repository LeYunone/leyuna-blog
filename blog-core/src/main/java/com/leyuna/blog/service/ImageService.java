package com.leyuna.blog.service;

import com.leyuna.blog.model.constant.DataResponse;
import org.springframework.stereotype.Service;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-22
 */
@Service
public class ImageService {

        /**
     * 用户请求上传文件
     *
     * @param remoteAddr
     * @return
     */
    public DataResponse requestUpImg(String remoteAddr) {
//        if (cacheExe.hasCacheByKey(remoteAddr + ":head")) {
//            //去找今天这个用户设置的头像
//            String cacheByKey = cacheExe.getCacheByKey(remoteAddr + ":head");
//            return DataResponse.buildFailure(cacheByKey);
//        }
        return DataResponse.buildSuccess();
    }
}
