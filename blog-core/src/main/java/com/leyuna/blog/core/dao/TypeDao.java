package com.leyuna.blog.core.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.core.dao.repository.entry.TypeDO;
import com.leyuna.blog.core.model.co.TypeCO;
import com.leyuna.blog.core.model.dto.TypeDTO;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TypeDao extends BaseDao<TypeDO> {

    Page<TypeCO> selectByCon(TypeDTO type);
}
