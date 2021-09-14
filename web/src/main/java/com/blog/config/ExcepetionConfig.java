package com.blog.config;

import com.blog.bean.ResponseBean;
import com.blog.bean.ResponseCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author pengli
 * @create 2021-08-31 15:34
 *
 * 全局异常捕捉类
 */
@ControllerAdvice
@ResponseBody
public class ExcepetionConfig {

    /**
     * 知识盲点， 前后端分离的前提下 后台怎么控制前台跳转页面呢 不会 2021-8-31
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseBean errorHander(HttpServletRequest request, Exception e){
        String message = e.getMessage();
        ResponseBean responseBean=new ResponseBean();
        responseBean.setCode(ResponseCode.ERROR_CODE);
        responseBean.setSrcData(message);
        return responseBean;
    }
}
