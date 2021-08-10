package com.blog.api.service;

import com.blog.api.dto.UserDTO;
import com.blog.daoservice.entry.User;

/**
 * @author pengli
 * @create 2021-08-10 15:43
 *
 * 用户相关服务类
 */
public interface UserService {

    UserDTO selectUserByConselectUser(UserDTO userDTO);
}
