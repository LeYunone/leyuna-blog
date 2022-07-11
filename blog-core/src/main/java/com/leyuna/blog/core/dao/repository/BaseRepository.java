package com.leyuna.blog.core.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyuna.blog.core.dao.BaseDao;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象Repository服务类
 * @author pengli
 * @since 2021-10-18
 */
public class BaseRepository<M extends BaseMapper<DO>, DO> extends ServiceImpl<M, DO> implements BaseDao<DO> {
    @Override
    public boolean insertOrUpdate(Object entity) {
        return false;
    }

    @Override
    public boolean insertOrUpdateBatch(List params) {
        return false;
    }

    @Override
    public boolean deleteById(Serializable id) {
        return false;
    }

    @Override
    public boolean deleteByIdBatch(List ids) {
        return false;
    }

    @Override
    public <R> boolean deleteLogicById(Serializable id, SFunction<DO, R> tableId) {
        return false;
    }

    @Override
    public Page<DO> selectByConPage(Object o, Page page) {
        return null;
    }

    @Override
    public <R> Page<DO> selectByConOrderPage(Object con, Page page, SFunction<DO, R> function, boolean isDesc) {
        return null;
    }

    @Override
    public List<DO> selectByCon(Object o) {
        return null;
    }

    @Override
    public List<DO> selectByCon(LambdaQueryWrapper<DO> queryWrapper) {
        return null;
    }

    @Override
    public List<DO> selectByCon(Object o, LambdaQueryWrapper<DO> queryWrapper) {
        return null;
    }

    @Override
    public <R> List<R> selectByCon(Object o, Class<R> clazz) {
        return null;
    }

    @Override
    public <R> List<R> selectByCon(Object o, Class<R> clazz, LambdaQueryWrapper<DO> queryWrapper) {
        return null;
    }

    @Override
    public <R> R selectById(Serializable id, Class<R> clazz) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public DO selectById(Serializable id) {
        return null;
    }

    @Override
    public <R> R selectOne(Object o, Class<R> clazz) {
        return null;
    }

    @Override
    public DO selectOne(Object o) {
        return null;
    }
}
