package com.leyuna.blog.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.TypeBean;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.gateway.TypeGateway;
import com.leyuna.blog.repository.entry.TypeDO;
import com.leyuna.blog.repository.mapper.TypeMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TypeRepository extends BaseRepository<TypeMapper, TypeDO, TypeCO> implements TypeGateway {
    /**
     * 分页模糊查询
     * @param
     * @param
     * @return
     */
    @Override
    public Page<TypeCO> selectByCon(TypeBean type) {
        Page page=new Page(type.getIndex(),type.getSize());
        TypeDO typeDO = TransformationUtil.copyToDTO(type, TypeDO.class);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(typeDO);
        IPage<TypeDO> Page = this.page(page, new QueryWrapper<TypeDO>()
                .allEq(stringObjectMap)
                .like(StringUtils.isNotBlank(type.getConditionName()),"type_Name",type.getConditionName()));
        return TransformationUtil.copyToPage(Page,TypeCO.class);
    }
}
