package com.blog.daoservice.impl;

import com.blog.daoservice.dao.TagDao;
import com.blog.daoservice.entry.Tag;
import com.blog.daoservice.mapper.TagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TagDaoImpl extends SysBaseMpImpl<Tag,TagMapper> implements TagDao {

    @Override
    public List<Tag> selectByIds(List<Integer> ids) {
        List<Tag> tags = this.baseMapper.selectBatchIds(ids);
        return tags;
    }
}
