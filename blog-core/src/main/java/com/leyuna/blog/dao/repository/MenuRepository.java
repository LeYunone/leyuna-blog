package com.leyuna.blog.dao.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.dao.BlogDao;
import com.leyuna.blog.dao.MenuDao;
import com.leyuna.blog.dao.repository.entry.BlogDO;
import com.leyuna.blog.dao.repository.entry.MenuDO;
import com.leyuna.blog.dao.repository.mapper.BlogMapper;
import com.leyuna.blog.dao.repository.mapper.MenuMapper;
import com.leyuna.blog.model.co.BlogCO;
import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.util.TransformationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (BlogDO)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Service
public class MenuRepository extends BaseRepository<MenuMapper, MenuDO> implements MenuDao {
}

