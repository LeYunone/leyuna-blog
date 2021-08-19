package com.blog.daoservice.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.daoservice.entry.Tag;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TagDao extends SysBaseDao<Tag>{

    List<Tag> selectByIds(List<Integer> ids);

    int deleteTagsByIds(List<Integer> ids);

    IPage<Tag> selectByLikeNamePage(Tag tag, Page<Tag> page, String conditionName);

    int getTagsCount();

    int getTagsCountByLikeName(String conditionName);

    boolean updateNameById(Tag tag);
}
