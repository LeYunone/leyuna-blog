package com.leyuna.blog.dao.repository;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.dao.TypeNavDao;
import com.leyuna.blog.domain.TypeNavE;
import com.leyuna.blog.gateway.TypeNavGateway;
import com.leyuna.blog.repository.entry.TypeNavDO;
import com.leyuna.blog.repository.mapper.TypeNavMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TypeNav)原子服务
 *
 * @author pengli
 * @since 2021-08-23 13:34:40
 */
@Service
public class TypeNavRepository extends BaseRepository<TypeNavMapper, TypeNavDO, TypeNavCO> implements TypeNavDao {

    /**
     * 定制查询
     * @param typeNavE
     * @return
     */
    @Override
    public List<TypeNavCO> selectByCon (TypeNavE typeNavE) {
        LambdaQueryWrapper<TypeNavDO> like = new QueryWrapper<TypeNavDO>().lambda()
                .like(StringUtils.isNotBlank(typeNavE.getTypeNavName()), TypeNavDO::getTypeNavName, typeNavE.getTypeNavName());
        List<TypeNavDO> typeNavs = this.baseMapper.selectList(like);
        return TransformationUtil.copyToLists(typeNavs,TypeNavCO.class);
    }
}

