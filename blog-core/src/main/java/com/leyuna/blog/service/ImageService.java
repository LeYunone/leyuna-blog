package com.leyuna.blog.service;

import com.leyuna.blog.model.constant.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-22
 */
@Service
public class ImageService {

    @Autowired
    private RedisService redisService;

        /**
     * 用户请求上传文件
     *
     * @param remoteAddr
     * @return
     */
    public DataResponse requestUpImg(String remoteAddr) {
        if (redisService.hasKey(remoteAddr + ":head")) {
            //去找今天这个用户设置的头像
            Object cacheByKey = redisService.getData(remoteAddr + ":head");
            return DataResponse.buildFailure(cacheByKey);
        }
        return DataResponse.buildSuccess();
    }
}
