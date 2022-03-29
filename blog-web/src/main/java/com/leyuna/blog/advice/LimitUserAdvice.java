package com.leyuna.blog.advice;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.constant.enums.UserErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author pengli
 * @create 2021-12-29 16:56
 * 前置：限制用户合法
 */
@Aspect
@Component
public class LimitUserAdvice {

    @Pointcut("execution(public * com.leyuna.blog.control.DiskControl.*(..))")
    public void before(){
    }

    @Before("before()")
    public void LimitUserLogin(){
        //判断用户登录
        AssertUtil.isFalse(!StpUtil.isLogin(), UserErrorEnum.LOGINT_NOT.getMsg());
        SaSession session = StpUtil.getSession();
        AssertUtil.isFalse(ObjectUtils.isEmpty(session), SystemErrorEnum.REQUEST_FAIL.getMsg());
        UserCO user=(UserCO)session.get("user");
        //判断用户合法
        AssertUtil.isFalse(ObjectUtils.isEmpty(user),UserErrorEnum.LOGINT_NOT.getMsg());
    }
}
