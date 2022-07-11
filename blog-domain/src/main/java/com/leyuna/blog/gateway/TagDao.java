package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.model.dto.TagDTO;
import com.leyuna.blog.co.blog.TagCO;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TagDao extends BaseDao<TagCO> {

    Page<TagCO> selectByCon(TagDTO tag);
}
