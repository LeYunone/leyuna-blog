package com.blog.daoservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import util.TransformationUtil;

import java.util.List;
import java.util.Map;


/**
 * @author pengli
 * @create 2021-08-10 14:50
 */
public class SysBaseMpImpl<E,M extends BaseMapper<E>>{
    @Autowired
    protected M baseMapper;

    public List<E> queryByCon(E e) {
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(e);
        List<E> es = baseMapper.selectList(new QueryWrapper<E>().allEq(stringObjectMap));
        return es;
    }
}
