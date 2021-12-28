package com.leyuna.blog.gateway;


import com.leyuna.blog.co.blog.TouristHeadCO;

/**
 * (TouristHead)dao
 *
 * @author
 * @since 2021-09-17 11:15:03
 */
public interface TouristHeadGateway extends BaseGateway<TouristHeadCO> {

    boolean updateHead (String ip, String head);
}

