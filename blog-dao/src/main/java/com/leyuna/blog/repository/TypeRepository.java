package com.leyuna.blog.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.TypeCO;
import com.leyuna.blog.entry.Type;
import com.leyuna.blog.gateway.TypeGateway;
import com.leyuna.blog.repository.mapper.TypeMapper;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.ErrorMeassage;
import com.leyuna.blog.util.ObjectUtil;
import com.leyuna.blog.util.TransformationUtil;
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
     * @param page
     * @return
     */
    @Override
    public IPage<Type> selectByLikeNamePage(Type type, Page<Type> page, String conditionName) {
        AssertUtil.isTrue(ObjectUtil.isNotNull (type), ErrorMeassage.OBJECT_NULL);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(type);
        IPage<Type> iPage = this.page(page, new QueryWrapper<Type>()
                .allEq(stringObjectMap).like("type_Name",conditionName));
        return iPage;
    }

    @Override
    public int getTagsCount(){
        return this.count();
    }

    @Override
    public boolean updateNameById(Type type){
        boolean update = this.update(new UpdateWrapper<Type>().lambda().eq(Type::getId, type.getId()).set(Type::getTypeName, type.getTypeName()));
        return update;
    }

    @Override
    public boolean updateLastUseTimeById(Integer id) {
        boolean update = this.update(new UpdateWrapper<Type>().lambda().eq(Type::getId, id).set(Type::getLastUserTime, LocalDateTime.now()));
        return update;
    }

    @Override
    public boolean updateUseCountByName(Integer id,int userCount){
        boolean update = this.update(new UpdateWrapper<Type>().lambda().eq(Type::getId,id).
                set(Type::getUseCount, userCount));
        return update;
    }
}
