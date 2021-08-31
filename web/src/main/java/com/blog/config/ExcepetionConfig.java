package com.blog.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pengli
 * @create 2021-08-31 15:34
 *
 * 全局异常捕捉类
 */
@ControllerAdvice
public class ExcepetionConfig {

    /**
     * 知识盲点， 前后端分离的前提下 后台怎么控制前台跳转页面呢 不会 2021-8-31
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Object errorHander(HttpServletRequest request,Exception e){
        ModelAndView md=new ModelAndView();
        md.addObject("url",request.getRequestURL());
        md.setViewName("/error");
        md.addObject("error",500);
        return md;
    }
}
