package com.blog.daoservice.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.daoservice.dao.BlogDao;
import com.blog.daoservice.entry.Blog;
import com.blog.daoservice.mapper.BlogMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import util.AssertUtil;
import util.ErrorMeassage;
import util.ObjectUtil;
import util.TransformationUtil;

import java.util.List;
import java.util.Map;

/**
 * (Blog)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Service
public class BlogDaoImpl extends SysBaseMpImpl<BlogMapper,Blog> implements BlogDao {

    @Override
    @Cacheable
    public IPage<Blog> queryByTagName(Blog e, Page<Blog> page) {
        IPage<Blog> iPage = this.baseMapper.selectPage(page, new QueryWrapper<Blog>().lambda().like(Blog::getTag,e.getTag()));
        return iPage;
    }

    public int queryCountByType(Integer type){
        return this.count(new QueryWrapper<Blog>().lambda().eq(Blog::getType,type));
    }


    public Blog queryByid(Integer blogId){
        return this.baseMapper.selectById(blogId);
    }

}

