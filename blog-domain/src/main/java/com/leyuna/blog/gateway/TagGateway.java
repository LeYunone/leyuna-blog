package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.blog.TagCO;
import com.leyuna.blog.domain.TagE;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TagGateway extends BaseGateway<TagCO> {

    Page<TagCO> selectByLikeNamePage (TagE tag, Integer index, Integer size, String conditionName);

    int getTagsCount ();

    int getTagsCountByLikeName (String conditionName);

    boolean updateLastUseTimeByName (String[] names);

    boolean updateUseCountByName (String name, int userCount);
}
