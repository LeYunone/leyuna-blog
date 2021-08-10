package com.blog.control;

import com.blog.api.domain.UserDomain;
import com.blog.bean.ResponseBean;
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
        userDomain.login(userName,passWord);
        return successResponseBean();
    }
}
