package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leyuna.blog.co.TagCO;
import com.leyuna.blog.domain.TagE;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TagGateway extends BaseGateway<TagCO> {

    IPage<TagCO> selectByLikeNamePage (TagE tag, Integer index,Integer size, String conditionName);

    int getTagsCount ();

    int getTagsCountByLikeName (String conditionName);

    boolean updateNameById (TagE tag);

    boolean updateLastUseTimeByName (String[] names);

    boolean updateUseCountByName (String name, int userCount);
}
