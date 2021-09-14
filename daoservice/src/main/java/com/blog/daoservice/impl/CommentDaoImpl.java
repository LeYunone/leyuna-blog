package com.blog.daoservice.impl;


import com.blog.daoservice.dao.CommentDao;
import com.blog.daoservice.entry.Comment;
import com.blog.daoservice.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
 * (Comment)原子服务
 *
 * @author
 * @since 2021-09-14 19:29:57
 */
@Service
public class CommentDaoImpl extends SysBaseMpImpl<CommentMapper, Comment> implements CommentDao {
}

