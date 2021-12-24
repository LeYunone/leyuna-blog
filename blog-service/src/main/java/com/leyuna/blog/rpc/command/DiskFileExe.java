package com.leyuna.blog.rpc.command;

import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-12-24 11:02
 */
@Service
public class DiskFileExe {

    @Autowired
    private LeyunaDiskRpcService leyunaDiskRpcService;

    public ResponseBean selectFile(String id){
        return leyunaDiskRpcService.selectFile(id);
    }

}
