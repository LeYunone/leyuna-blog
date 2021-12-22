package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.TypeCO;
import com.leyuna.blog.entry.Type;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TypeGateway extends BaseGateway<TypeCO> {

    IPage<Type> selectByLikeNamePage (Type type, Page<Type> page, String conditionName);

    int getTagsCount ();

    boolean updateNameById (Type type);

    boolean updateLastUseTimeById (Integer id);

    boolean updateUseCountByName (Integer id, int userCount);
}
