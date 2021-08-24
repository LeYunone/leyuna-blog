package com.blog.daoservice.dao;

import com.blog.daoservice.entry.TypeNav;

import java.util.List;


/**
 * (TypeNav)dao
 *
 * @author pengli
 * @since 2021-08-23 13:34:40
 */
public interface TypeNavDao extends SysBaseDao<TypeNav> {

   List<TypeNav> queryAllTypeNavConditionName(String conditionName);
}

