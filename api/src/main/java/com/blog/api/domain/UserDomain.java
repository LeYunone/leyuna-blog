package com.blog.api.domain;

import com.blog.api.command.TokenExe;
import com.blog.api.command.UserExe;
import com.blog.api.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 15:35
 *
 * 用户领域活动类
 */
@Service
public class UserDomain {

    @Autowired
    private UserExe userExe;

    @Autowired
    private TokenExe tokenExe;

    /**
     * 用户在领域内登录操作
     * @param userName
     * @param passWord
     * @return
     */
    public UserDTO login(String userName, String passWord){
        UserDTO userDTO=UserDTO.builder().userName(userName).build();
        UserDTO user = userExe.selectUserByConselectUser(userDTO);
        if(null==user){
            //返回空对象
            return null;
        }else{
            UserDTO build = UserDTO.builder().userName(userName).passWord(passWord).build();
            UserDTO result = userExe.selectUserByConselectUser(build);
            //如果登录成功，则取得用户的id
            return result;
        }
    }

    /**
     * 用户token上锁操作
     */
    public void userLock(Integer id){
        tokenExe.loginToken(id);
    }
}
