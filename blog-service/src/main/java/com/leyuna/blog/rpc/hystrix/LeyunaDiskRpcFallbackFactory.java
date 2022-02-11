package com.leyuna.blog.rpc.hystrix;

import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.constant.ResponseCode;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pengli
 * @create 2021-12-24 13:39
 */
@Component
@Log4j2
public class LeyunaDiskRpcFallbackFactory implements FallbackFactory<LeyunaDiskRpcService> {
    @Override
    public LeyunaDiskRpcService create (Throwable throwable) {
        return new LeyunaDiskRpcService() {
            @Override
            public ResponseBean selectFile (FileQueryBean queryBean) {
                return response(throwable);
            }

            @Override
            public ResponseBean<Double> selectAllFileSize (String userId) {
                return response(throwable);
            }

            @Override
            public ResponseBean<Integer> requestSaveFile (String userId, MultipartFile files) {
                return response(throwable);
            }

            @Override
            public ResponseBean saveFile (String userId, MultipartFile files, String saveTime) {
                return response(throwable);
            }

            @Override
            public ResponseBean deleteFile (String id) {
                return response(throwable);
            }

            @Override
            public ResponseEntity downloadFile (String id,String userId) {
                return null;
            }
        };
    }

    private ResponseBean response(Throwable cause){
        String errMsg = cause.getMessage();
        log.error(errMsg);
        if (cause instanceof HystrixTimeoutException) {
            return ResponseBean.buildFailure(ResponseCode.RPC_TIMEOUT);
        }
        if (errMsg != null && errMsg.contains("Load balancer does not have available server for client")) {
            return ResponseBean.buildFailure(ResponseCode.RPC_ERROR_503);
        }
        //微服务端的指定异常
        if(errMsg.contains("message")){
            return ResponseBean.buildFailure("远程服务调用未知错误:"+errMsg.substring(errMsg.indexOf("message:")+8));
        }
        return ResponseBean.buildFailure(ResponseCode.RPC_UNKNOWN_ERROR);
    }
}
