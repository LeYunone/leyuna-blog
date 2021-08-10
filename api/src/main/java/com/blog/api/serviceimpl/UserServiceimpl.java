package com.blog.api.serviceimpl;

import com.blog.api.dto.UserDTO;
import com.blog.api.service.UserService;
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
public class UserServiceimpl implements UserService {

    @Autowired
    private UserDao userDao;

    public UserDTO selectUserByConselectUser(UserDTO userDTO) {
        User user = TransformationUtil.copyToDTO(userDTO, User.class);
        List<User> users =
                userDao.queryByCon(user);
        User first = CollectionUtil.getFirst(users);
        if(null!=first){
            userDTO.setId(first.getId());
            return userDTO;
        }else{
            return null;
        }
    }
}
