package com.blog.daoservice.impl;


import com.blog.daoservice.dao.BlogDao;
import com.blog.daoservice.entry.Blog;
import com.blog.daoservice.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
 * (Blog)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Service
public class BlogDaoImpl extends SysBaseMpImpl<Blog, BlogMapper> implements BlogDao {
}

