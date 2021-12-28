package com.leyuna.blog.control;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.bean.UserBean;
import com.leyuna.blog.co.UserCO;
import com.leyuna.blog.error.UserAsserts;
import com.leyuna.blog.service.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pengli
 * @create 2021-08-10 15:02
 *
 * 用户控制器- 控制用户行为
 */
@RestController()
@RequestMapping("/user")
public class UserControl  {

    @Autowired
    private UserDomain userDomain;

    @PostMapping("/login")
    public ResponseBean login(@RequestBody UserBean user){
        UserCO login = userDomain.login(user.getUserName(),user.getPassWord());
        if(login!=null){
            //绑定令牌
            SaTokenInfo saTokenInfo = userDomain.userLock(login.getId());
            return ResponseBean.of(saTokenInfo);
        }else{
            return ResponseBean.buildFailure(UserAsserts.LOGINT_FAIL.getMsg());
        }
    }
}
