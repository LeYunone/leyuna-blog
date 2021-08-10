package com.blog.daoservice.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author pengli
 * @create 2021-08-10 14:50
 */
public class SysBaseMpImpl <M extends BaseMapper<T>,T> {
    public int insert(Object entity) {
        return 0;
    }

    public int deleteById(Serializable id) {
        return 0;
    }

    public int delete(Wrapper queryWrapper) {
        return 0;
    }

    public int updateById(Object entity) {
        return 0;
    }

    public int update(Object entity, Wrapper updateWrapper) {
        return 0;
    }

    public T selectById(Serializable id) {
        return null;
    }

    public T selectOne(Wrapper queryWrapper) {
        return null;
    }

    public Integer selectCount(Wrapper queryWrapper) {
        return null;
    }

    public List selectList(Wrapper queryWrapper) {
        return null;
    }

    public List<Map<String, Object>> selectMaps(Wrapper queryWrapper) {
        return null;
    }

    public List<Object> selectObjs(Wrapper queryWrapper) {
        return null;
    }

    public IPage<Map<String, Object>> selectMapsPage(IPage page, Wrapper queryWrapper) {
        return null;
    }

    public IPage selectPage(IPage page, Wrapper queryWrapper) {
        return null;
    }

    public List selectByMap(Map columnMap) {
        return null;
    }

    public List selectBatchIds(Collection idList) {
        return null;
    }

    public int deleteBatchIds(Collection idList) {
        return 0;
    }

    public int deleteByMap(Map columnMap) {
        return 0;
    }
}
