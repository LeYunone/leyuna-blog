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

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-20
 */
@Service
public class OssService {

    @Value("${endpoint:}")
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    @Value("${accessKeyId:}")
    private String accessKeyId = "LTAI5tShL2DWt7PoFbLWBR5i";
    @Value("${accessKeySecret:}")
    private String accessKeySecret = "nxm4XgX9rUsJNwCfWAqlErLMitg0WC";
    @Value("${bucketName:}")
    private String bucketName = "leyuna-blog-img";
    @Value("${bucketUrl:}")
    private String bucketUrl = "leyuna-blog-img.oss-cn-hangzhou.aliyuncs.com";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String uploadImage(MultipartFile file){
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            return null;
        }

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //当前日的文件夹
        String objectname = "image/" + DateUtil.today()+"/";
        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = objectname+ UUID.randomUUID()+type;
        //调用oss实现上传第一个参数bucket名称  第二个参数文件名称  第三个参数输入流
        ossClient.putObject(bucketName, url, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        //返回组成的文件url
        String imgUrl = "https://" + bucketUrl+ "/" + url;
        logger.debug("UPLOADFILE===SUCCESS =====================URL:"+imgUrl);
        return imgUrl;
    }

}
