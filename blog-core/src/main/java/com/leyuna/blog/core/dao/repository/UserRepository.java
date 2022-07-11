package com.leyuna.blog.core.dao.repository;

import com.leyuna.blog.core.dao.UserDao;
import com.leyuna.blog.core.dao.repository.entry.UserDO;
import com.leyuna.blog.core.dao.repository.mapper.UserMapper;
import com.leyuna.blog.core.model.co.UserCO;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class UserRepository extends BaseRepository<UserMapper, UserDO, UserCO> implements UserDao {

}
