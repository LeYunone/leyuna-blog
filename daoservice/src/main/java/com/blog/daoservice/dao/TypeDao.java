package com.blog.daoservice.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.daoservice.entry.Type;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TypeDao extends SysBaseDao<Type>{

    IPage<Type> selectByLikeNamePage(Type type, Page<Type> page, String conditionName);

    int getTagsCount();

    boolean updateNameById(Type type);

    boolean updateLastUseTimeById(Integer id);

    boolean updateUseCountByName(Integer id,int userCount);
}
