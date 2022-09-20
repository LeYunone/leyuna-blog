package com.leyuna.blog.service;

import cn.hutool.core.collection.CollectionUtil;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.constant.enums.UploadImgTypeEnum;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.UpLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-28
 */
@Service
public class FileService {

    @Autowired
    private HttpServletRequest request;


    public List<String> uploadFile(List<MultipartFile> files, String type) {
        AssertUtil.isFalse(CollectionUtil.isEmpty(files), "文件为空");
        String path = "";
        String message = "";
        //表情包上传
        if (UploadImgTypeEnum.EMO_IMG.getValue().equals(type)) {
            path = ServerCode.EMO_SAVE_PATH;
            message = ServerCode.SERVER_EMO_SAVE_PATH + file.getOriginalFilename();
        }
        //一般图片上传
        if (UploadImgTypeEnum.IMAGE_IMG.getValue().equals(type)) {
            String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            path = ServerCode.IMG_SAVE_PATH + format;
            message = ServerCode.SERVER_IMG_SAVE_PATH + format + "/" + file.getOriginalFilename();
        }
        //上传服务器
        UpLoadUtil.uploadFile(path, file);
        return DataResponse.of(message);
    }
}
