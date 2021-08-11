package com.blog.api.service;

/**
 * @author pengli
 * @create 2021-08-10 16:02
 */
public interface TokenService {

    void loginToken(Integer id);

    boolean logoutToken();
}
