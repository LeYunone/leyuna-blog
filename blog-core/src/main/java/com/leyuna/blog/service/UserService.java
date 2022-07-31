package com.leyuna.blog.core.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyuna.blog.core.constant.enums.UserErrorEnum;
import com.leyuna.blog.core.dao.UserDao;
import com.leyuna.blog.core.dao.repository.entry.UserDO;
import com.leyuna.blog.core.model.co.UserCO;
import com.leyuna.blog.core.model.constant.DataResponse;
import com.leyuna.blog.core.model.dto.UserDTO;
import com.leyuna.blog.core.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 15:35
 * <p>
 * 用户领域活动类
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    /**
     * 用户在领域内登录操作
     *
     * @return
     */
    public SaTokenInfo login(UserDTO userDTO) {
        UserDO user = userDao.selectOne(userDTO);
        AssertUtil.isFalse(ObjectUtil.isNull(user), UserErrorEnum.LOGINT_FAIL.getMsg());
        //绑定令牌
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        //设置session user
        StpUtil.getSession().set("user", user);
        return tokenInfo;
    }

    /**
     * 检测当前环境用户是否有锁
     */
    public void checkLock() {
        try {
            StpUtil.checkLogin();
        } catch (Exception e) {
            AssertUtil.isTrue(e.getMessage());
        }
    }
}
