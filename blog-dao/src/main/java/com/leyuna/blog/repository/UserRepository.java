package com.leyuna.blog.repository;

import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.entry.User;
import com.leyuna.blog.gateway.UserGateway;
import com.leyuna.blog.repository.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class UserRepository extends BaseRepository<UserMapper, User, UserCO> implements UserGateway {

}
