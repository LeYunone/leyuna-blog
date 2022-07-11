package com.leyuna.blog.gateway;


import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.domain.TypeNavE;

import java.util.List;

/**
 * (TypeNav)dao
 *
 * @author pengli
 * @since 2021-08-23 13:34:40
 */
public interface TypeNavDao extends BaseDao<TypeNavCO> {

    List<TypeNavCO> selectByCon (TypeNavE typeNavE);
}

