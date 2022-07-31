package com.leyuna.blog.dao.repository;

import com.leyuna.blog.dao.UserDao;
import com.leyuna.blog.dao.repository.entry.UserDO;
import com.leyuna.blog.dao.repository.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class UserRepository extends BaseRepository<UserMapper, UserDO> implements UserDao {

}
