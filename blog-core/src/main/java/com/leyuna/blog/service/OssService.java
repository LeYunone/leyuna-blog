package com.leyuna.blog.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-20
 */
@Service
public class OssService {

    @Value("${oss.endpoint:}")
    private String endpoint;
    @Value("${oss.accessKeyId:}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret:}")
    private String accessKeySecret;
    @Value("${oss.bucketName:}")
    private String bucketName;
    @Value("${oss.bucketUrl:}")
    private String bucketUrl;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String uploadFile(MultipartFile file, String dirName) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            return null;
        }

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //当前日的文件夹
        String objectname = dirName + "/" + DateUtil.today() + "/";
        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = objectname + UUID.randomUUID() + type;
        //调用oss实现上传第一个参数bucket名称  第二个参数文件名称  第三个参数输入流
        ossClient.putObject(bucketName, url, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        //返回组成的文件url
        String imgUrl = "https://" + bucketUrl + "/" + url;
        logger.debug("UPLOADFILE===SUCCESS =====================URL:" + imgUrl);
        return imgUrl;
    }

    public List<String> uploadFile(List<MultipartFile> files, String dirName) {
        List<String> resulUrl = new ArrayList<>();
        for(MultipartFile file : files){
            String url = this.uploadFile(file, dirName);
            resulUrl.add(url);
        }
        return resulUrl;
    }

}
