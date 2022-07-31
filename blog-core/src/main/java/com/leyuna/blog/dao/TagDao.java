package com.leyuna.blog.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.dao.repository.entry.TagDO;
import com.leyuna.blog.model.co.TagCO;
import com.leyuna.blog.model.dto.TagDTO;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TagDao extends BaseDao<TagDO> {

    Page<TagCO> queryTag(TagDTO tag);
}
