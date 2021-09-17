package com.blog.daoservice.dao;

import com.blog.daoservice.entry.TouristHead;


import java.io.Serializable;

/**
 * (TouristHead)dao
 *
 * @author
 * @since 2021-09-17 11:15:03
 */
public interface TouristHeadDao extends SysBaseDao<TouristHead> {

    boolean updateHead(String ip,String head);
}

