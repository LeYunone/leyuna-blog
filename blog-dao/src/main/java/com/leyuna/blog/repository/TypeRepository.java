package com.leyuna.blog.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.TypeBean;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.gateway.TypeGateway;
import com.leyuna.blog.repository.mapper.TypeMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TypeRepository extends BaseRepository<TypeMapper,Type, TypeCO> implements TypeGateway {
    /**
     * 分页模糊查询
     * @param
     * @param
     * @return
     */
    @Override
    public Page<TypeCO> selectLikePage(TypeBean type) {
        Page page=new Page(type.getIndex(),type.getSize());
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(type);
        IPage<Type> Page = this.page(page, new QueryWrapper<Type>()
                .allEq(stringObjectMap)
                .like(StringUtils.isNotBlank(type.getConditionName()),"type_Name",type.getConditionName()));
        return TransformationUtil.copyToPage(Page,TypeCO.class);
    }

    @Override
    public int getTagsCount(){
        return this.count();
    }

    @Override
    public boolean updateLastUseTimeById(String id) {
        boolean update = this.update(new UpdateWrapper<Type>().lambda().eq(Type::getId, id).set(Type::getupdateDt, LocalDateTime.now()));
        return update;
    }

    @Override
    public boolean updateUseCountByName(String id,int userCount){
        boolean update = this.update(new UpdateWrapper<Type>().lambda().eq(Type::getId,id).
                set(Type::getUseCount, userCount));
        return update;
    }
}
