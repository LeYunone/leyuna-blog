package com.blog.daoservice.dao;

import com.blog.daoservice.entry.Tag;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TagDao extends SysBaseDao<Tag>{

    List<Tag> selectByIds(List<Integer> ids);
}
