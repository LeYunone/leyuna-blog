package com.leyuna.blog.core.dao;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;

/**
 *基础Geteway
 * @author pengli
 * @since 2021-10-18
 */
public interface BaseDao<DO> {


    /**
     * 插入或更新 ： 带id更新  反之插入
     * @return
     */
    boolean insertOrUpdate(Object entity);

    /**
     * 批量插入
     * @param params  会通过copy转换为实体
     * @return
     */
    boolean insertOrUpdateBatch(List params);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    boolean deleteById(Serializable id);

    /**
     * 根据ids批量删除
     * @param ids
     * @return
     */
    boolean deleteByIdBatch(List ids);

    <R> boolean deleteLogicById(Serializable id, SFunction<DO, R> tableId);


    /**
     * 万能eq分页查询
     * @return
     */
    Page<DO> selectByConPage(Object o, Page page);

    /**
     * 万能eq分页查询 根据condition和isDesc排序查询
     * @param con
     * @param isDesc
     * @return
     */
    <R>Page<DO> selectByConOrderPage(Object con, Page page, SFunction<DO, R> function, boolean isDesc);

    List<DO> selectByCon(Object o);

    List<DO> selectByCon(LambdaQueryWrapper<DO> queryWrapper);

    List<DO> selectByCon(Object o, LambdaQueryWrapper<DO> queryWrapper);

    <R> List<R> selectByCon(Object o, Class<R> clazz);

    <R> List<R> selectByCon(Object o, Class<R> clazz, LambdaQueryWrapper<DO> queryWrapper);

    <R> R selectById(Serializable id, Class<R> clazz) throws IllegalAccessException, InstantiationException;

    DO selectById(Serializable id);

    <R> R selectOne(Object o, Class<R> clazz);

    DO selectOne(Object o);
}
