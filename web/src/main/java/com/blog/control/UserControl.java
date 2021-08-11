package com.blog.control;

import cn.dev33.satoken.stp.StpUtil;
import com.blog.api.domain.UserDomain;
import com.blog.api.dto.UserDTO;
import com.blog.bean.ResponseBean;
import com.blog.error.UserAsserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengli
 * @create 2021-08-10 15:02
 *
 * 用户控制器- 控制用户行为
 */
@RestController
public class UserControl extends SysBaseControl{

    @Autowired
    private UserDomain userDomain;

    @RequestMapping("/login")
    public ResponseBean login(String userName,String passWord){
        UserDTO login = userDomain.login(userName, passWord);
        if(login!=null){
            //绑定令牌
            userDomain.userLock(login.getId());
            return successResponseBean(null);
        }else{
            return failResponseBean(UserAsserts.LOGINT_FAIL);
        }
    }
}
