package com.blog.web.control;

import com.blog.web.bean.ResponseBean;
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

    @RequestMapping("/login")
    public ResponseBean login(String userName,String passWord){

        return successResponseBean();
    }
}
