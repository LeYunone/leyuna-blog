package com.leyuna.blog.core.dao.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.core.dao.BlogDao;
import com.leyuna.blog.core.dao.repository.entry.BlogDO;
import com.leyuna.blog.core.dao.repository.mapper.BlogMapper;
import com.leyuna.blog.core.model.co.BlogCO;
import com.leyuna.blog.core.model.dto.BlogDTO;
import com.leyuna.blog.core.util.TransformationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (BlogDO)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Service
public class BlogRepository extends BaseRepository<BlogMapper, BlogDO> implements BlogDao {

    /**
     * 定制查询
     * @param blog
     * @return
     */
    @Override
    public Page<BlogCO> queryBlog(BlogDTO blog) {
        Page page=new Page(blog.getIndex(),blog.getSize());
        IPage<BlogDO> Page = this.baseMapper.selectPage(page,
                new QueryWrapper<BlogDO>().lambda()
                        .like(StrUtil.isNotBlank(blog.getTitle()),BlogDO::getTitle,blog.getTitle())
                        .like(StrUtil.isNotBlank(blog.getTag()),BlogDO::getTag,blog.getTag())
                        .eq(StrUtil.isNotBlank(blog.getType()),BlogDO::getType,blog.getType())
                        .in(null!=blog.getBlogType(),BlogDO::getBlogType,blog.getBlogType())
                        .orderByDesc(BlogDO::getCreateDt));
        return TransformationUtil.copyToPage(Page, BlogCO.class);
    }

    @Override
    public List<BlogCO> selectRandomList () {
        return this.baseMapper.selectRandomList();
    }
}

