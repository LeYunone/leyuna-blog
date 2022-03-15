package com.leyuna.blog.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.gateway.BlogGateway;
import com.leyuna.blog.repository.mapper.BlogMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * (BlogDO)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Service
public class BlogRepository extends BaseRepository<BlogMapper, BlogDO, BlogCO> implements BlogGateway {

    @Override
    public Page<BlogCO> queryBlog(BlogBean blog) {
        Page page=new Page(blog.getIndex(),blog.getSize());
        IPage<BlogDO> Page = this.baseMapper.selectPage(page,
                new QueryWrapper<BlogDO>().lambda()
                        .like(StringUtils.isNotBlank(blog.getTitle()),BlogDO::getTitle,blog.getTitle())
                        .like(StringUtils.isNotBlank(blog.getTag()),BlogDO::getTag,blog.getTag())
                        .eq(StringUtils.isNotBlank(blog.getType()),BlogDO::getType,blog.getType())
                        .eq(null!=blog.getBlogType(),BlogDO::getBlogType,blog.getBlogType())

                        .orderByDesc(BlogDO::getcreateDt));
        return TransformationUtil.copyToPage(Page,BlogCO.class);
    }

}

