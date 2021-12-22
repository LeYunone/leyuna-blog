package com.leyuna.blog.gateway;


import com.leyuna.blog.co.TypeNavCO;

import java.util.List;

/**
 * (TypeNav)dao
 *
 * @author pengli
 * @since 2021-08-23 13:34:40
 */
public interface TypeNavGateway extends BaseGateway<TypeNavCO> {

   List<TypeNavCO> queryAllTypeNavConditionName (String conditionName);
}

