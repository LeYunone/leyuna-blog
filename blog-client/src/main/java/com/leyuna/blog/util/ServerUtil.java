package com.leyuna.blog.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pengli
 * @create 2021-09-18 15:50
 *
 *
 * 服务器工具类
 */
public class ServerUtil {

    public static String getClientIp(HttpServletRequest request){
        if(request.getHeader("x-forwarded-for") == null){
            return request.getRemoteAddr();
        }

        //x-forwarded-for是
        return request.getHeader("x-forwarded-for");
    }
}
