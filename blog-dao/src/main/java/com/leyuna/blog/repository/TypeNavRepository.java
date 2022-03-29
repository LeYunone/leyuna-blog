package com.leyuna.blog.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.domain.TypeNavE;
import com.leyuna.blog.gateway.TypeNavGateway;
import com.leyuna.blog.repository.entry.TypeNavDO;
import com.leyuna.blog.repository.mapper.TypeNavMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * (TypeNav)原子服务
 *
 * @author pengli
 * @since 2021-08-23 13:34:40
 */
@Service
public class TypeNavRepository extends BaseRepository<TypeNavMapper, TypeNavDO, TypeNavCO> implements TypeNavGateway {

    /**
     * 定制查询
     * @param typeNavE
     * @return
     */
    @Override
    public List<TypeNavCO> selectByCon (TypeNavE typeNavE) {
        TypeNavDO typeNavDO = TransformationUtil.copyToDTO(typeNavE, TypeNavDO.class);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(typeNavDO);
        List<TypeNavDO> typeNavs = this.baseMapper.selectList(new QueryWrapper<TypeNavDO>()
                .allEq(stringObjectMap)
                .like(StringUtils.isNotBlank(typeNavE.getTypeNavName()), "type_nav_name", typeNavE.getTypeNavName()));
        return TransformationUtil.copyToLists(typeNavs,TypeNavCO.class);
    }
}

