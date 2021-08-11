package com.blog.api.serviceimpl;

import cn.dev33.satoken.stp.StpUtil;
import com.blog.api.service.TokenService;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 16:02
 *
 * 控制令牌的服务
 */
@Service
public class TokenServiceimpl implements TokenService {

    public void loginToken(Integer id) {
        StpUtil.login(id);
    }

    public boolean logoutToken() {
        return false;
    }
}
