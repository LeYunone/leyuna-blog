package com.blog.daoservice.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.daoservice.dao.TypeNavDao;
import com.blog.daoservice.entry.TypeNav;
import com.blog.daoservice.mapper.TypeNavMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TypeNav)原子服务
 *
 * @author pengli
 * @since 2021-08-23 13:34:40
 */
@Service
public class TypeNavDaoImpl extends SysBaseMpImpl<TypeNavMapper, TypeNav> implements TypeNavDao {

    public List<TypeNav> queryAllTypeNavConditionName(String conditionName){
        List<TypeNav> typeNavs = this.baseMapper.selectList(new QueryWrapper<TypeNav>().lambda()
                .like(TypeNav::getTypeNavName, conditionName));
        return typeNavs;
    }
}

