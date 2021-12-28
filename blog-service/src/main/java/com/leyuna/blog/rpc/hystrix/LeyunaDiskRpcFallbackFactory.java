package com.leyuna.blog.rpc.hystrix;

import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.bean.UpFileBean;
import com.leyuna.blog.constant.ResponseCode;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import com.netflix.hystrix.exception.HystrixTimeoutException;
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
            public ResponseBean selectFile (String id) {
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
        ResponseCode basicCode = ResponseCode.RPC_UNKNOWN_ERROR;

        if (throwable instanceof HystrixTimeoutException) {
            basicCode = ResponseCode.RPC_TIMEOUT;
        }

        if (errMsg != null && errMsg.contains("Load balancer does not have available server for client")) {
            basicCode = ResponseCode.RPC_ERROR_503;
        }

        return ResponseBean.buildFailure(basicCode);
    }
}
