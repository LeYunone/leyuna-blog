package com.blog.daoservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.daoservice.dao.TypeDao;
import com.blog.daoservice.entry.Type;
import com.blog.daoservice.mapper.TypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.AssertUtil;
import util.ErrorMeassage;
import util.ObjectUtil;
import util.TransformationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TypeDaoImpl extends SysBaseMpImpl<TypeMapper,Type> implements TypeDao {

    @Override
    public List<Type> selectByIds(List<Integer> ids) {
        List<Type> types = this.baseMapper.selectBatchIds(ids);
        return types;
    }

    @Override
    public Type selectById(Integer id) {
        Type type = this.baseMapper.selectById(id);
        return type;
    }

    @Transactional
    @Override
    public int deleteTypesByIds(List<Integer> ids) {
        int i = this.baseMapper.deleteBatchIds(ids);
        return i;
    }

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
