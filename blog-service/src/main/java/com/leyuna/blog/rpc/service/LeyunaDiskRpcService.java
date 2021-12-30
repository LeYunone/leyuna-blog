package com.leyuna.blog.rpc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.rpc.hystrix.LeyunaDiskRpcFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author pengli
 * @create 2021-12-23 16:36
 * 乐云一云盘 微服务
 */
@FeignClient(value = "LEYUNA-DISK",fallbackFactory = LeyunaDiskRpcFallbackFactory.class)
public interface LeyunaDiskRpcService {

    @RequestMapping(value = "/file/selectFile",method = RequestMethod.POST)
    ResponseBean<Page<FileInfoCO>> selectFile( FileQueryBean queryBean);

    @RequestMapping(value = "/file/requestSaveFile",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseBean<List<MultipartFile>> requestSaveFile(@RequestParam("userId")String userId,@RequestParam("files") MultipartFile file);

    @RequestMapping(value = "/file/saveFile",method = RequestMethod.POST)
    ResponseBean saveFile( UpFileBean upFileBean);

    @RequestMapping(value = "/file/deleteFile",method = RequestMethod.POST)
    ResponseBean deleteFile(@RequestParam("id") String id);

    @RequestMapping(value = "/file/downloadFile",method = RequestMethod.POST)
    ResponseEntity downloadFile(@RequestParam("id") String id);
}
