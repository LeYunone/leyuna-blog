package com.leyuna.blog.command;

import com.blog.api.dto.UserDTO;
import com.blog.daoservice.dao.UserDao;
import com.blog.daoservice.entry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.CollectionUtil;
import util.TransformationUtil;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-10 15:43
 *
 * 用户相关服务类
 */
@Service
public class UserExe{

    @Autowired
    private UserDao userDao;

    public UserDTO selectUserByCon(UserDTO userDTO) {
        User user = TransformationUtil.copyToDTO(userDTO, User.class);
        List<User> users =
                userDao.selectByCon(user);
        User first = CollectionUtil.getFirst(users);
        if(null!=first){
            userDTO.setId(first.getId());
            return userDTO;
        }else{
            return null;
        }
    }
}
