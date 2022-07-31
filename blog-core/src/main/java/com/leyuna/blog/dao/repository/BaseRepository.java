package com.leyuna.blog.dao.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyuna.blog.dao.BaseDao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 抽象Repository服务类
 *
 * @author pengli
 * @since 2021-10-18
 */
public class BaseRepository<M extends BaseMapper<DO>, DO> extends ServiceImpl<M, DO> implements BaseDao<DO> {

    private Class do_Class;

    public BaseRepository() {
        Class<?> c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) t).getActualTypeArguments();
            this.do_Class = (Class<?>) params[1];
        }
    }

    @Override
    public boolean insertOrUpdate(Object entity) {
        DO aDo = castToDO(entity);
        return this.saveOrUpdate(aDo);
    }

    @Override
    public boolean insertOrUpdateBatch(List params) {
        List list = BeanUtil.copyToList(params, do_Class);
        return this.saveOrUpdateBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean deleteByIdBatch(List ids) {
        return super.removeByIds(ids);
    }

    @Override
    public <R> boolean deleteLogicById(Serializable id, SFunction<DO, R> tableId) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("isDeleted", 1);
        LambdaUpdateWrapper lambda = updateWrapper.lambda();
        lambda.eq(tableId, id);
        return super.update(lambda);
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
        return (List<DO>) this.selectByCon(o, do_Class);
    }

    @Override
    public List<DO> selectByCon(LambdaQueryWrapper<DO> queryWrapper) {
        return this.selectByCon(null,queryWrapper);
    }

    @Override
    public List<DO> selectByCon(Object o, LambdaQueryWrapper<DO> queryWrapper) {
        return this.selectByCon(o,do_Class,queryWrapper);
    }

    @Override
    public <R> List<R> selectByCon(Object o, Class<R> clazz) {
        return this.selectByCon(o,clazz,null);
    }

    @Override
    public <R> List<R> selectByCon(Object o, Class<R> clazz, LambdaQueryWrapper<DO> queryWrapper) {
        DO d = castToDO(o);
        deletedToFalse(d);
        List<DO> dos = this.getConQueryResult(d, queryWrapper);
        return BeanUtil.copyToList(dos,clazz);
    }

    @Override
    public <R> R selectById(Serializable id, Class<R> clazz) {
        DO aDo = this.baseMapper.selectById(id);

        R r =  JSONUtil.toBean(JSONUtil.toJsonStr(aDo), clazz);
        return  r;
    }

    @Override
    public DO selectById(Serializable id) {
        return (DO)this.selectById(id,do_Class);
    }

    @Override
    public <R> R selectOne(Object o, Class<R> clazz) {
        List<R> rs = this.selectByCon(o, clazz);
        if (CollectionUtils.isNotEmpty(rs)) {
            return rs.get(0);
        } else {
            return null;
        }
    }

    @Override
    public DO selectOne(Object o) {
        return (DO)this.selectOne(o,do_Class);
    }

    private DO castToDO(Object o) {
        DO d = null;
        try {
            d = (DO) do_Class.newInstance();
        } catch (Exception e) {
        }
        if (null != o) {
            BeanUtil.copyProperties(o, d);
        }
        return d;
    }


     /**
     * 查询规则：条件对象 与 自定义Lambda条件 AND 拼接
     * @param o
     * @param queryWrapper
     * @return
     */
    private List<DO> getConQueryResult(Object o, LambdaQueryWrapper<DO> queryWrapper) {
        if (null == queryWrapper) {
            queryWrapper = new LambdaQueryWrapper<>();
        }
        if(null!=o){
            DO jDO = (DO) JSONUtil.toBean(JSONUtil.toJsonStr(o), do_Class);
            queryWrapper.setEntity(jDO);
        }
        return this.baseMapper.selectList(queryWrapper);
    }


    /**
     * 如果包含字段deleted，且值为空，那么给上默认值0
     *
     * @param con
     */
    private void deletedToFalse(Object con) {
        if(null==con) return;
        Class<?> aClass = con.getClass();
        try {
            Field deletedField = aClass.getDeclaredField("isDeleted");
            boolean accessible = deletedField.isAccessible();
            try {
                if (!accessible) {
                    deletedField.setAccessible(true);
                }
                Object value = deletedField.get(con);
                if (value == null) {
                    deletedField.set(con, "0");
                }
            } finally {
                if (!accessible) {
                    deletedField.setAccessible(accessible);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }
}
