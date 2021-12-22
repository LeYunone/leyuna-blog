package com.leyuna.blog.command;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 16:02
 *
 * 控制令牌的服务
 */
@Service
public class TokenExe {

    public void loginToken(String id) {
        StpUtil.login(id);
    }

    public boolean logoutToken() {
        return false;
    }
}
