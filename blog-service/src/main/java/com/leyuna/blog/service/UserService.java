package com.leyuna.blog.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.UserBean;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.command.TokenExe;
import com.leyuna.blog.command.UserExe;
import com.leyuna.blog.error.UserErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 15:35
 *
 * 用户领域活动类
 */
@Service
public class UserService {

    @Autowired
    private UserExe userExe;

    @Autowired
    private TokenExe tokenExe;

    /**
     * 用户在领域内登录操作
     * @return
     */
    public DataResponse login(UserBean userBean){
        UserCO user = userExe.selectUserByCon(userBean);
        if(user!=null){
            //绑定令牌
            SaTokenInfo saTokenInfo = tokenExe.loginToken(user.getId());
            //设置session user
            StpUtil.getSession().set("user",user);
            return DataResponse.of(saTokenInfo);
        }else{
            return DataResponse.buildFailure(UserErrorEnum.LOGINT_FAIL.getMsg());
        }
    }

    /**
     * 检测当前环境用户是否有锁
     */
    public void checkLock(){
        try {
            StpUtil.checkLogin();
        }catch (Exception e){
            AssertUtil.isTrue(e.getMessage());
        }
    }
}
