package com.leyuna.blog.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.dao.repository.entry.TypeDO;
import com.leyuna.blog.model.co.TypeCO;
import com.leyuna.blog.model.dto.TypeDTO;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TypeDao extends BaseDao<TypeDO> {

    Page<TypeCO> queryType(TypeDTO type);
}
