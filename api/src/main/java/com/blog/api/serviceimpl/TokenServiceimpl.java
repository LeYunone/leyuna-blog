package com.blog.api.serviceimpl;

import cn.dev33.satoken.stp.StpUtil;
import com.blog.api.service.TokenService;
import com.blog.daoservice.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author pengli
 * @create 2021-08-10 16:02
 */
public class TokenServiceimpl implements TokenService {

    @Autowired
    private UserDao userDao;

    public boolean loginToken(String userName, String passWord) {
        userDao
        StpUtil.login();
        return false;
    }

    public boolean logoutToken() {
        return false;
    }
}
