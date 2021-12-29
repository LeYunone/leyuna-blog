package com.leyuna.blog.rpc.hystrix;

import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import com.leyuna.blog.util.AssertUtil;
import feign.hystrix.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author pengli
 * @create 2021-12-24 13:39
 */
@Component
public class LeyunaDiskRpcFallbackFactory implements FallbackFactory<LeyunaDiskRpcService> {
    @Override
    public LeyunaDiskRpcService create (Throwable throwable) {
        return new LeyunaDiskRpcService() {
            @Override
            public ResponseBean selectFile (FileQueryBean queryBean) {
                return response(throwable);
            }

            @Override
            public ResponseBean requestSaveFile (UpFileBean upFileBean) {
                return response(throwable);
            }

            @Override
            public ResponseBean saveFile (UpFileBean upFileBean) {
                return response(throwable);
            }

            @Override
            public ResponseBean deleteFile (String id) {
                return response(throwable);
            }

            @Override
            public ResponseEntity downloadFile (String id) {
                return null;
            }
        };
    }

    private ResponseBean response(Throwable throwable){
        String errMsg = throwable.getMessage();
        AssertUtil.isFalse(true,errMsg);
        return ResponseBean.buildFailure(errMsg);
    }
}