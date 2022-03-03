package com.leyuna.blog.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.command.TokenExe;
import com.leyuna.blog.command.UserExe;
import com.leyuna.blog.domain.UserE;
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
     * @param userName
     * @param passWord
     * @return
     */
    public UserCO login(String userName, String passWord){
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)){
            return null;
        }
        UserE userDTO=UserE.queryInstance().setUserName(userName);
        UserCO user = userExe.selectUserByCon(userDTO);
        if(null==user){
            //返回空对象
            return null;
        }else{
            UserE build = UserE.queryInstance().setUserName(userName).setPassWord(passWord);
            UserCO result = userExe.selectUserByCon(build);
            //如果登录成功，则取得用户的id
            return result;
        }
    }

    /**
     * 用户token上锁操作
     */
    public SaTokenInfo userLock(String id){
        return tokenExe.loginToken(id);
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
