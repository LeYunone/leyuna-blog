package com.blog.daoservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.daoservice.dao.UserDao;
import com.blog.daoservice.entry.User;
import com.blog.daoservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class UserDaoImpl extends SysBaseMpImpl<UserMapper,User> implements UserDao {

}
