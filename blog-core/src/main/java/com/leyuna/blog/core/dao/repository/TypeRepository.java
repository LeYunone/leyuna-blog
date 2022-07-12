package com.leyuna.blog.core.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.core.dao.TypeDao;
import com.leyuna.blog.core.dao.repository.entry.TypeDO;
import com.leyuna.blog.core.dao.repository.mapper.TypeMapper;
import com.leyuna.blog.core.model.co.TypeCO;
import com.leyuna.blog.core.model.dto.TypeDTO;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TypeRepository extends BaseRepository<TypeMapper, TypeDO> implements TypeDao {
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
