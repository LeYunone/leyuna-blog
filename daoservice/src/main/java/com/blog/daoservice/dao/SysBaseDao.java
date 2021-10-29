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
public interface SysBaseDao<E> extends IService<E>{

    boolean deleteByIds(List<Integer> ids);

    E selectById(int id);

    List<E> selectByIds(List<Integer> ids);

    boolean deleteById(int id);

    boolean create(E e);

    boolean batchCreate(List<E> es);

    List<E> selectByCon(E m);

    IPage<E> selectByConPage(E e, Page<E> page);

    IPage<E> selectByConPageOrderCreateTime(E e, Page<E> page,Integer type);
}
