package com.blog.daoservice.dao;


import java.util.List;

/**
 * @author pengli
 * @create 2021-08-10 16:07
 *
 * 原子服务基类方法
 */
public interface SysBaseDao<T>{

    List<T> queryByCon(T m);
}
