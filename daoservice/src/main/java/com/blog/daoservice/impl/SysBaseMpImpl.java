package com.blog.daoservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.daoservice.dao.SysBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import util.AssertUtil;
import util.ErrorMeassage;
import util.ObjectUtil;
import util.TransformationUtil;

import java.util.List;
import java.util.Map;


/**
 * @author pengli
 * @create 2021-08-10 14:50
 */
public class SysBaseMpImpl<M extends BaseMapper<E>,E> extends ServiceImpl<M,E> implements SysBaseDao<E> {
    @Autowired(required = false)
    protected M baseMapper;

    @Override
    public boolean batchCreate(List<E> es){
        return this.saveBatch(es);
    }

    @Override
    public boolean create(E e){
        return this.save(e);
    }

    @Override
    public boolean deleteByIds(List<Integer> ids){
        return this.baseMapper.deleteBatchIds(ids)>0;
    }

    @Override
    public boolean deleteById(int id){
        return this.baseMapper.deleteById(id)>0;
    }

    @Override
    public List<E> selectByIds(List<Integer> ids){
        return this.baseMapper.selectBatchIds(ids);
    }

    @Override
    public E selectById(int id){
        return this.baseMapper.selectById(id);
    }

    @Override
    public List<E> selectByCon(E e) {
        AssertUtil.isTrue(ObjectUtil.isNotNull (e), ErrorMeassage.OBJECT_NULL);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(e);
        List<E> es = this.baseMapper.selectList(new QueryWrapper<E>().allEq(stringObjectMap));
        return es;
    }

    @Override
    public IPage<E> selectByConPage(E e, Page<E> page) {
        AssertUtil.isTrue(ObjectUtil.isNotNull (e), ErrorMeassage.OBJECT_NULL);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(e);
        IPage<E> iPage = this.baseMapper.selectPage(page, new QueryWrapper<E>().allEq(stringObjectMap));
        return iPage;
    }

    /**
     * 分页查询 日期排序
     * @param e
     * @param page
     * @param type 排序类型   0为查询最近的日期排序   1为查最早的日期排序
     * @return
     */
    @Override
    public IPage<E> selectByConPageOrderCreateTime(E e, Page<E> page,Integer type) {
        AssertUtil.isTrue(ObjectUtil.isNotNull (e), ErrorMeassage.OBJECT_NULL);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(e);
        IPage<E> iPage =null;
        if(type==0){
             iPage=this.baseMapper.selectPage(page, new QueryWrapper<E>().allEq(stringObjectMap).orderByDesc("create_time"));
        }else{
            iPage=this.baseMapper.selectPage(page, new QueryWrapper<E>().allEq(stringObjectMap).orderByAsc("create_time"));
        }
        return iPage;
    }
}
