package com.blog.daoservice.impl;

import com.blog.daoservice.dao.UserDao;
import com.blog.daoservice.entry.User;
import com.blog.daoservice.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class UserDaoImpl extends SysBaseMpImpl<User,UserMapper> implements UserDao {
}
