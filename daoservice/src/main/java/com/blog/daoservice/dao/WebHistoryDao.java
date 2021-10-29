package com.blog.daoservice.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.daoservice.entry.WebHistory;


/**
 * (WebHistory)dao
 *
 * @author pengli
 * @since 2021-08-26 16:00:43
 */
public interface WebHistoryDao extends SysBaseDao<WebHistory> {

    /**
     * 模糊查询根据标题
     * @param index
     * @param size
     * @param conditionName
     * @return
     */
    IPage<WebHistory> selectByLikeNamePage(Integer index, Integer size, String conditionName);
}

