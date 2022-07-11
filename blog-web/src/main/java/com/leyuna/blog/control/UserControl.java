package com.leyuna.blog.control;

import cn.dev33.satoken.stp.StpUtil;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.model.dto.UserDTO;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.service.UserService;
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
    private UserService userService;

    /**
     * 简单登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public DataResponse login(@RequestBody UserDTO user){
        return userService.login(user);
    }

    /**
     * 获取登录信息 token?
     * @return
     */
    @GetMapping("/getLoginInfo")
    public DataResponse getLoginInfo(){
        UserCO user = (UserCO) StpUtil.getSession().get("user");
        if(ObjectUtils.isEmpty(user)){
            return DataResponse.buildFailure();
        }
        return DataResponse.of(user);
    }
}
