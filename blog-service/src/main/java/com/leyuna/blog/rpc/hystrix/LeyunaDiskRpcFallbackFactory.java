package com.leyuna.blog.rpc.hystrix;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.co.disk.FileValidatorCO;
import com.leyuna.blog.constant.code.ResponseCode;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;
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
            public DataResponse selectFile (FileQueryBean queryBean) {
                return response(throwable);
            }

            @Override
            public DataResponse<Double> selectAllFileSize (String userId) {
                return response(throwable);
            }

            @Override
            public DataResponse<FileValidatorCO> requestSaveFile (String userId, MultipartFile files) {
                return response(throwable);
            }

            @Override
            public DataResponse saveFile (String userId, MultipartFile files, String saveTime) {
                return response(throwable);
            }

            @Override
            public DataResponse deleteFile (String id,String userId) {
                return response(throwable);
            }

            @Override
            public DataResponse<FileInfoCO> downloadFile (String id, String userId) {
                return response(throwable);
            }
        };
    }

    private DataResponse response(Throwable cause){
        String errMsg = cause.getMessage();
        log.error(errMsg);
        if (cause instanceof HystrixTimeoutException) {
            return DataResponse.buildFailure(ResponseCode.RPC_TIMEOUT);
        }
        if (errMsg != null && errMsg.contains("Load balancer does not have available server for client")) {
            return DataResponse.buildFailure(ResponseCode.RPC_ERROR_503);
        }
        //微服务端的指定异常
        if(errMsg.contains("message")){
            return DataResponse.buildFailure("远程服务调用未知错误:"+errMsg.substring(errMsg.indexOf("message:")+8));
        }
        return DataResponse.buildFailure(ResponseCode.RPC_UNKNOWN_ERROR);
    }
}
