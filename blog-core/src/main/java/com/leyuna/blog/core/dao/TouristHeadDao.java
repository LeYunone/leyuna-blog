package com.leyuna.blog.core.dao;


import com.leyuna.blog.core.dao.repository.entry.TouristHeadDO;

/**
 * (TouristHead)dao
 *
 * @author
 * @since 2021-09-17 11:15:03
 */
public interface TouristHeadDao extends BaseDao<TouristHeadDO> {

    boolean updateHead (String ip, String head);
}

