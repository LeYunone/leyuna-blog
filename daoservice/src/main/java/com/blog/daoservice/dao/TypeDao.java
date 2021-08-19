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

    List<Type> selectByIds(List<Integer> ids);

    int deleteTypesByIds(List<Integer> ids);

    IPage<Type> selectByLikeNamePage(Type type, Page<Type> page, String conditionName);

    int getTagsCount();

    int getTagsCountByLikeName(String conditionName);

    boolean updateNameById(Type type);
}
