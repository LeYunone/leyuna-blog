package com.leyuna.blog.dao.repository;

import com.leyuna.blog.dao.FileDao;
import com.leyuna.blog.dao.repository.entry.FileDO;
import com.leyuna.blog.dao.repository.mapper.FileMapper;
import org.springframework.stereotype.Repository;

/**
 * (BlogDO)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Repository
public class FileRepository extends BaseRepository<FileMapper, FileDO> implements FileDao {

}

