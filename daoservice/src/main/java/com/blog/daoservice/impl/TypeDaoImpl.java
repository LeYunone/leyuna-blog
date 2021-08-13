package com.blog.daoservice.impl;

import com.blog.daoservice.dao.TypeDao;
import com.blog.daoservice.entry.Tag;
import com.blog.daoservice.entry.Type;
import com.blog.daoservice.mapper.TypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    @Override
    public int deleteTypesByIds(List<Integer> ids) {
        int i = this.baseMapper.deleteBatchIds(ids);
        return i;
    }
}
