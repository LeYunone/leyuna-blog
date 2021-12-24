package com.leyuna.blog.rpc.control;

import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.service.file.DiskDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengli
 * @create 2021-12-24 10:49
 */
@RestController
@RequestMapping("/file")
public class FileRpcControl {

    @Autowired
    private DiskDomain fileDomain;

    @RequestMapping("/selectFile")
    public ResponseBean selectFile (String id) {
        return fileDomain.selectFile(id);
    }
}
