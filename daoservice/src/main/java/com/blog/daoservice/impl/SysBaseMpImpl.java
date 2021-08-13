package com.blog.daoservice.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.daoservice.dao.SysBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import util.AssertUtil;
import util.ErrorMeassage;
import util.ObjectUtil;
import util.TransformationUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


/**
 * @author pengli
 * @create 2021-08-10 14:50
 */
public class SysBaseMpImpl<M extends BaseMapper<E>,E> implements SysBaseDao<E> {
    @Autowired(required = false)
    protected M baseMapper;

    public List<E> queryByCon(E e) {
        AssertUtil.isTrue(ObjectUtil.isNotNull (e), ErrorMeassage.OBJECT_NULL);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(e);
        List<E> es = this.baseMapper.selectList(new QueryWrapper<E>().allEq(stringObjectMap));
        return es;
    }

    @Override
    public boolean save(E entity) {
        return false;
    }

    @Override
    public boolean saveBatch(Collection<E> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<E> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        return false;
    }

    @Override
    public boolean remove(Wrapper<E> queryWrapper) {
        return false;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return false;
    }

    @Override
    public boolean updateById(E entity) {
        return false;
    }

    @Override
    public boolean update(E entity, Wrapper<E> updateWrapper) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<E> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(E entity) {
        return false;
    }

    @Override
    public E getById(Serializable id) {
        return null;
    }

    @Override
    public Collection<E> listByIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public Collection<E> listByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public E getOne(Wrapper<E> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<E> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<E> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public int count(Wrapper<E> queryWrapper) {
        return 0;
    }

    @Override
    public List<E> list(Wrapper<E> queryWrapper) {
        return null;
    }

    @Override
    public IPage<E> page(IPage<E> page, Wrapper<E> queryWrapper) {
        return null;
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<E> queryWrapper) {
        return null;
    }

    @Override
    public <V> List<V> listObjs(Wrapper<E> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<E> page, Wrapper<E> queryWrapper) {
        return null;
    }

    @Override
    public BaseMapper<E> getBaseMapper() {
        return null;
    }
}
