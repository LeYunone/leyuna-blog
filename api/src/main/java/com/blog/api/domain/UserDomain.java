package com.blog.api.domain;

import com.blog.api.dto.UserDTO;
import com.blog.api.service.TokenService;
import com.blog.api.service.UserService;
import com.blog.api.serviceimpl.UserServiceimpl;
import com.blog.daoservice.dao.UserDao;
import com.blog.daoservice.entry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.CollectionUtil;
import util.TransformationUtil;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-10 15:35
 *
 * 用户领域活动类
 */
@Service
public class UserDomain {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 用户在领域内登录操作
     * @param userName
     * @param passWord
     * @return
     */
    public UserDTO login(String userName, String passWord){
        UserDTO userDTO=UserDTO.builder().userName(userName).build();
        UserDTO user = userService.selectUserByConselectUser(userDTO);
        if(null==user){
            //返回空对象
            return null;
        }else{
            UserDTO build = UserDTO.builder().userName(userName).passWord(passWord).build();
            UserDTO result = userService.selectUserByConselectUser(build);
            //如果登录成功，则取得用户的id
            return result;
        }
    }

    /**
     * 用户token上锁操作
     */
    public void userLock(Integer id){
        tokenService.loginToken(id);
    }
}
