//package com.leyuna.blog.advice;
//
//import com.leyuna.blog.constant.code.SystemErrorEnum;
//import com.leyuna.blog.constant.enums.UserErrorEnum;
//import com.leyuna.blog.util.AssertUtil;
//import com.leyuna.blog.util.ServerUtil;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author pengli
// * @create 2021-09-14 16:25
// */
//@Aspect
//@Component
//public class LimitIpAdvice {
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Pointcut("execution(public * com.leyuna.blog.control.TouristControl.comment(..))")
//    public void before () {
//    }
//
//    /**
//     * 限制用户ip评论
//     */
//    @Before("before()")
//    public void limitIpComment () {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        AssertUtil.isFalse(null == request, SystemErrorEnum.REQUEST_FAIL.getMsg());
//        //获取此次请求用户ip
//        String remoteAddr = ServerUtil.getClientIp(request);
//        AssertUtil.isFalse(stringRedisTemplate.hasKey(remoteAddr), UserErrorEnum.REQUEST_FREQUENTLY_FAIL.getMsg());
//
//        //加入次用户ip限制，最快10秒评论一次
//        stringRedisTemplate.opsForValue().set(remoteAddr, "1", 10 * 1, TimeUnit.SECONDS);
//    }
//}
