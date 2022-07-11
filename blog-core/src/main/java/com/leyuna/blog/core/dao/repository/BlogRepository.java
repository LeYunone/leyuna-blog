package com.leyuna.blog.core.dao.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.core.dao.BlogDao;
import com.leyuna.blog.gateway.BlogGateway;
import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.repository.entry.BlogDO;
import com.leyuna.blog.repository.mapper.BlogMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (BlogDO)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Service
public class BlogRepository extends BaseRepository<BlogMapper, BlogDO, BlogCO> implements BlogDao {

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
                        .like(StringUtils.isNotBlank(blog.getTitle()),BlogDO::getTitle,blog.getTitle())
                        .like(StringUtils.isNotBlank(blog.getTag()),BlogDO::getTag,blog.getTag())
                        .eq(StringUtils.isNotBlank(blog.getType()),BlogDO::getType,blog.getType())
                        .in(null!=blog.getBlogType(),BlogDO::getBlogType,blog.getBlogType())
                        .orderByDesc(BlogDO::getCreateDt));
        return TransformationUtil.copyToPage(Page,BlogCO.class);
    }

    @Override
    public List<BlogCO> selectRandomList () {
        return this.baseMapper.selectRandomList();
    }
}

