package com.blog.api.service;

/**
 * @author pengli
 * @create 2021-08-10 16:02
 */
public interface TokenService {

    boolean loginToken(String userName,String passWord);

    boolean logoutToken();
}
