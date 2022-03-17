package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.TypeBean;
import com.leyuna.blog.co.blog.TypeCO;

/**
 * @author pengli
 * @create 2021-08-12 13:28
 */
public interface TypeGateway extends BaseGateway<TypeCO> {

    Page<TypeCO> selectByCon(TypeBean type);
}
