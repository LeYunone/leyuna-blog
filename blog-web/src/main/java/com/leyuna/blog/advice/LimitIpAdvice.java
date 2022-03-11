package com.leyuna.blog.advice;

import com.leyuna.blog.error.SystemErrorEnum;
import com.leyuna.blog.error.UserErrorEnum;
import com.leyuna.blog.util.ServerUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author pengli
 * @create 2021-09-14 16:25
 */
@Aspect
@Component
public class LimitIpAdvice {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.leyuna.blog.control.TouristControl.commpent(..))")
    public void before(){
    }

    /**
     * 限制用户ip评论
     */
    @Before("before()")
    public void limitIpComment(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if(request==null){
            throw new RuntimeException(SystemErrorEnum.REQUEST_FAIL.getMsg());
        }
        //获取此次请求用户ip
        String remoteAddr = ServerUtil.getClientIp(request);
        if(!stringRedisTemplate.hasKey(remoteAddr)){
            //加入次用户ip限制，最快一分钟评论一次
            stringRedisTemplate.opsForValue().set(remoteAddr,"1",30*1, TimeUnit.SECONDS);
        }else{
            throw new RuntimeException(UserErrorEnum.REQUEST_FREQUENTLY_FAIL.getMsg());
        }
    }
}
