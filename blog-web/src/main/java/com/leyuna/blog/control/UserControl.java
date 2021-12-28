package com.leyuna.blog.control;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.bean.blog.UserBean;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.error.UserAsserts;
import com.leyuna.blog.service.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
            //设置session user
            StpUtil.getSession().set("user",login);
            return ResponseBean.of(saTokenInfo);
        }else{
            return ResponseBean.buildFailure(UserAsserts.LOGINT_FAIL.getMsg());
        }
    }

    @GetMapping("/getLoginInfo")
    public ResponseBean getLoginInfo(){
        UserCO user = (UserCO) StpUtil.getSession().get("user");
        if(ObjectUtils.isEmpty(user)){
            return ResponseBean.buildFailure();
        }
        return ResponseBean.of(user);
    }
}
