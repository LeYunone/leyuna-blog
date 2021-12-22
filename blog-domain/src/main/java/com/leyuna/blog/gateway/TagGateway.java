package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.TagCO;
import com.leyuna.blog.entry.Tag;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TagGateway extends BaseGateway<TagCO> {

    IPage<Tag> selectByLikeNamePage (Tag tag, Page<Tag> page, String conditionName);

    int getTagsCount ();

    int getTagsCountByLikeName (String conditionName);

    boolean updateNameById (Tag tag);

    boolean updateLastUseTimeByName (String[] names);

    boolean updateUseCountByName (String name, int userCount);
}
