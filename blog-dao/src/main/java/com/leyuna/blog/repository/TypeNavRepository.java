package com.leyuna.blog.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyuna.blog.co.TypeNavCO;
import com.leyuna.blog.entry.TypeNav;
import com.leyuna.blog.gateway.TypeNavGateway;
import com.leyuna.blog.repository.mapper.TypeNavMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TypeNav)原子服务
 *
 * @author pengli
 * @since 2021-08-23 13:34:40
 */
@Service
public class TypeNavRepository extends BaseRepository<TypeNavMapper, TypeNav, TypeNavCO> implements TypeNavGateway {

    public List<TypeNav> queryAllTypeNavConditionName(String conditionName){
        List<TypeNav> typeNavs = this.baseMapper.selectList(new QueryWrapper<TypeNav>().lambda()
                .like(TypeNav::getTypeNavName, conditionName));
        return typeNavs;
    }
}

