package com.blog.daoservice.dao;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-10 16:07
 *
 * 原子服务基类方法
 */
public interface SysBaseDao<T> extends IService<T>{

    List<T> queryByCon(T m);

    IPage<T> queryByConPage(T e, Page<T> page);

    IPage<T> queryByConPageOrderCreateTime(T e, Page<T> page,Integer type);
}
