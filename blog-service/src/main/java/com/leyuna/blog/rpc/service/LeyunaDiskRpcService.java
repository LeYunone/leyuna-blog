package com.leyuna.blog.rpc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.rpc.hystrix.LeyunaDiskRpcFallbackFactory;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pengli
 * @create 2021-12-23 16:36
 * 乐云一云盘 微服务
 */
@FeignClient(value = "LEYUNA-DISK",fallbackFactory = LeyunaDiskRpcFallbackFactory.class,configuration = LeyunaDiskRpcService.MultipartSupportConfig.class )
public interface LeyunaDiskRpcService {

    @Configuration
    class MultipartSupportConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;
        @Bean
        public Encoder feignEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }

    /**
     * 分页 - 条件 查询文件
     * @param queryBean
     * @return
     */
    @RequestMapping(value = "/file/selectFile",method = RequestMethod.POST)
    ResponseBean<Page<FileInfoCO>> selectFile( FileQueryBean queryBean);

    /**
     * 获得指定用户的当前文件内存
     * @param userId
     * @return
     */
    @RequestMapping(value = "/file/selectAllFileSize",method = RequestMethod.GET)
    ResponseBean<Double> selectAllFileSize( @RequestParam("userId") String userId);


    /**
     * 用户请求上传文件
     * @param userId
     * @param file
     * @return
     */
    @RequestMapping(value = "/file/requestSaveFile",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseBean<Integer> requestSaveFile(@RequestParam(value = "userId",required = false)String userId,
                                                      @RequestPart(value = "file") MultipartFile  file);

    /**
     * 上传文件
     * @param userId
     * @param file
     * @param saveTime
     * @return
     */
    @RequestMapping(value = "/file/saveFile",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseBean saveFile(@RequestParam(value = "userId",required = false)String userId,
                          @RequestPart(value = "file") MultipartFile  file,
                          @RequestParam(value = "saveTime",required = false)String saveTime);

    @RequestMapping(value = "/file/deleteFile",method = RequestMethod.POST)
    ResponseBean deleteFile(@RequestParam(value = "id") String id);

    @RequestMapping(value = "/file/downloadFile",method = RequestMethod.POST)
    ResponseEntity downloadFile(@RequestParam(value = "id") String id,@RequestParam(value = "userId") String userId);
}
