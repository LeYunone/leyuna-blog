package com.blog.api.domain;

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
 * @create 2021-08-10 15:35
 *
 * 用户领域活动类
 */
@Service
public class UserDomain {

    @Autowired
    private UserDao userDao;

    /**
     * 用户在领域内登录操作
     * @param userName
     * @param passWord
     * @return
     */
    public UserDTO login(String userName, String passWord){
        List<User> users =
                userDao.queryByCon(User.builder().userName(userName).build());
        if(users.size()==0){
            return null;
        }else{
            List<User> byPassWord =
                    userDao.queryByCon(User.builder().userName(userName).passWord(passWord).build());
            if(byPassWord.size()!=0){
                User first = CollectionUtil.getFirst(byPassWord);
                UserDTO userDTO = TransformationUtil.copyToDTO(byPassWord, UserDTO.class);
                return userDTO;
            }else{
                return null;
            }
        }
    }
}
