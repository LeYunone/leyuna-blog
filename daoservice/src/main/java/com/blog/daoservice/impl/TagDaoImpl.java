package com.blog.daoservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.daoservice.dao.TagDao;
import com.blog.daoservice.entry.Tag;
import com.blog.daoservice.mapper.TagMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.AssertUtil;
import util.ErrorMeassage;
import util.ObjectUtil;
import util.TransformationUtil;

import java.util.List;
import java.util.Map;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TagDaoImpl extends SysBaseMpImpl<TagMapper,Tag> implements TagDao {

    @Override
    public List<Tag> selectByIds(List<Integer> ids) {
        List<Tag> tags = this.baseMapper.selectBatchIds(ids);
        return tags;
    }

    @Transactional
    @Override
    public int deleteTagsByIds(List<Integer> ids) {
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
    @Cacheable
    public IPage<Tag> selectByLikeNamePage(Tag tag, Page<Tag> page,String conditionName) {
        AssertUtil.isTrue(ObjectUtil.isNotNull (tag), ErrorMeassage.OBJECT_NULL);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(tag);
        IPage<Tag> iPage = this.baseMapper.selectPage(page, new QueryWrapper<Tag>()
                .allEq(stringObjectMap).like("tag_Name",conditionName));
        return iPage;
    }

    @Override
    public int getTagsCount(){
        int count = this.count();
        return count;
    }

    @Override
    public int getTagsCountByLikeName(String conditionName) {
        return this.count(new QueryWrapper<Tag>().lambda().like(Tag::getTagName,conditionName));
    }
}
