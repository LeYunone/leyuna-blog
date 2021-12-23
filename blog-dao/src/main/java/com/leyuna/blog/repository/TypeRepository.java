package com.leyuna.blog.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.TypeCO;
import com.leyuna.blog.domain.TypeE;
import com.leyuna.blog.entry.Type;
import com.leyuna.blog.error.ErrorMessage;
import com.leyuna.blog.gateway.TypeGateway;
import com.leyuna.blog.repository.mapper.TypeMapper;
import com.leyuna.blog.util.AssertUtil;
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
    public Page<TypeCO> selectByLikeNamePage(TypeE type,Integer index,Integer size, String conditionName) {
        AssertUtil.isTrue(ObjectUtil.isNotNull (type), ErrorMessage.OBJECT_NULL);
        Page page=new Page(index,size);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(type);
        IPage<Type> Page = this.page(page, new QueryWrapper<Type>()
                .allEq(stringObjectMap).like("type_Name",conditionName));
        return TransformationUtil.copyToPage(Page,TypeCO.class);
    }

    @Override
    public int getTagsCount(){
        return this.count();
    }

    @Override
    public boolean updateLastUseTimeById(String id) {
        boolean update = this.update(new UpdateWrapper<Type>().lambda().eq(Type::getId, id).set(Type::getLastUserTime, LocalDateTime.now()));
        return update;
    }

    @Override
    public boolean updateUseCountByName(String id,int userCount){
        boolean update = this.update(new UpdateWrapper<Type>().lambda().eq(Type::getId,id).
                set(Type::getUseCount, userCount));
        return update;
    }
}
