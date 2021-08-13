package com.blog.daoservice.dao;

import com.blog.daoservice.entry.Type;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TypeDao extends SysBaseDao<Type>{

    List<Type> selectByIds(List<Integer> ids);

    int deleteTypesByIds(List<Integer> ids);
}
