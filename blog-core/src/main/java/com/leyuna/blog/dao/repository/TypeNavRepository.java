package com.leyuna.blog.dao.repository;


import com.leyuna.blog.dao.TypeNavDao;
import com.leyuna.blog.dao.repository.entry.TypeNavDO;
import com.leyuna.blog.dao.repository.mapper.TypeNavMapper;
import org.springframework.stereotype.Service;

/**
 * (TypeNav)原子服务
 *
 * @author pengli
 * @since 2021-08-23 13:34:40
 */
@Service
public class TypeNavRepository extends BaseRepository<TypeNavMapper, TypeNavDO> implements TypeNavDao {
}

