package com.leyuna.blog.core.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.model.dto.TypeDTO;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TypeDao extends BaseDao<TypeCO> {

    Page<TypeCO> selectByCon(TypeDTO type);
}
