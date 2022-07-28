package com.leyuna.blog.command;

import com.leyuna.blog.model.dto.UserDTO;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.domain.UserE;
import com.leyuna.blog.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author pengli
 * @create 2021-08-10 15:43
 *
 * 用户相关服务类
 */
@Service
public class UserExe{

    public UserCO selectUserByCon(UserDTO userDTO) {
        List<UserCO> users = UserE.of(userDTO).selectByCon();
        UserCO first = CollectionUtil.getFirst(users);
        return first;
    }
}
