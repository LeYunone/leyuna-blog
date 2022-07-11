package com.leyuna.blog.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.model.dto.TypeDTO;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.gateway.TypeDao;
import com.leyuna.blog.repository.entry.TypeDO;
import com.leyuna.blog.repository.mapper.TypeMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TypeRepository extends BaseRepository<TypeMapper, TypeDO, TypeCO> implements TypeDao {
    /**
     * 定制查询
     * @param
     * @param
     * @return
     */
    @Override
    public Page<TypeCO> selectByCon(TypeDTO type) {
        Page page=new Page(type.getIndex(),type.getSize());
        LambdaQueryWrapper<TypeDO> like = new QueryWrapper<TypeDO>().lambda()
                .like(StringUtils.isNotBlank(type.getTypeName()), TypeDO::getTypeName, type.getTypeName());
        IPage<TypeDO> Page = this.page(page,like);
        return TransformationUtil.copyToPage(Page,TypeCO.class);
    }
}
