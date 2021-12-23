package com.leyuna.blog.bean;

import com.leyuna.blog.constant.ResponseCode;
import lombok.*;

/**
 * @author pengli
 * @create 2021-08-10 15:05
 *   一般响应结果集
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ResponseBean<T> {

    private T data;

    private boolean status;

    private String message;

    private String code;

    public ResponseBean() {
    }

    public static <T> ResponseBean<T> of(T data) {
        ResponseBean<T> response = new ResponseBean();
        response.setStatus(true);
        response.setData(data);
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(ResponseCode.SUCCESS.getDesc());
        return response;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseBean<T> of(boolean status, ResponseCode responseCode, T data) {
        ResponseBean<T> response = new ResponseBean();
        response.setStatus(status);
        response.setData(data);
        response.setCode(responseCode.getCode());
        response.setMessage(responseCode.getDesc());
        return response;
    }

    public static ResponseBean buildFailure(ResponseCode responseCode) {
        return of(false, responseCode, (Object)null);
    }

    public static ResponseBean buildFailure(String message) {
        ResponseBean response = of(false, ResponseCode.ERROR, (Object)null);
        response.setMessage(message);
        return response;
    }

    public static ResponseBean buildFailure(){
        ResponseBean response = new ResponseBean();
        response.setStatus(false);
        response.setCode(ResponseCode.ERROR.getCode());
        response.setMessage(ResponseCode.ERROR.getDesc());
        return response;
    }

    public static ResponseBean buildSuccess() {
        ResponseBean response = new ResponseBean();
        response.setStatus(true);
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(ResponseCode.SUCCESS.getDesc());
        return response;
    }


}
