package com.leyuna.blog.core.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.core.dao.repository.entry.TagDO;
import com.leyuna.blog.core.model.co.TagCO;
import com.leyuna.blog.core.model.dto.TagDTO;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TagDao extends BaseDao<TagDO> {

    Page<TagCO> queryTag(TagDTO tag);
}
