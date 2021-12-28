package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.domain.TypeE;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TypeGateway extends BaseGateway<TypeCO> {

    Page<TypeCO> selectByLikeNamePage (TypeE type, Integer index, Integer size, String conditionName);

    int getTagsCount ();

    boolean updateLastUseTimeById (String id);

    boolean updateUseCountByName (String id, int userCount);
}
