package com.leyuna.blog.command;

import cn.dev33.satoken.stp.SaTokenInfo;
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

    public SaTokenInfo loginToken(String id) {
        StpUtil.login(id);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return tokenInfo;
    }
}
