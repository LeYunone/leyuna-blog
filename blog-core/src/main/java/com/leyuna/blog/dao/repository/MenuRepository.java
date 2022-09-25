package com.leyuna.blog.dao.repository;

import com.leyuna.blog.dao.MenuDao;
import com.leyuna.blog.dao.repository.entry.MenuDO;
import com.leyuna.blog.dao.repository.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
 * (BlogDO)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Service
public class MenuRepository extends BaseRepository<MenuMapper, MenuDO> implements MenuDao {
}

