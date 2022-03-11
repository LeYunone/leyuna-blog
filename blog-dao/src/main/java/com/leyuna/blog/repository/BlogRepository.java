package com.leyuna.blog.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.entry.Blog;
import com.leyuna.blog.gateway.BlogGateway;
import com.leyuna.blog.repository.mapper.BlogMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * (Blog)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Service
public class BlogRepository extends BaseRepository<BlogMapper, Blog, BlogCO> implements BlogGateway {

    @Override
    public Page<BlogCO> queryByTagName (BlogE e, Integer index , Integer size) {
        Page page=new Page(index,size);
        IPage<Blog> Page = this.baseMapper.selectPage(page, new QueryWrapper<Blog>().lambda().
                like(Blog::getTag,e.getTag()).orderByDesc(Blog::getCreateTime));
        return TransformationUtil.copyToPage(Page,BlogCO.class);
    }

    @Override
    public int queryCountByType(String type){
        return this.count(new QueryWrapper<Blog>().lambda().eq(Blog::getType,type));
    }

    @Override
    public boolean updateClickCount(String blogId,Integer clickCount){
        return this.update(new UpdateWrapper<Blog>().lambda().eq(Blog::getId,blogId).set(Blog::getClickCount,clickCount));
    }

    @Override
    public Page<BlogCO> queryBlog(BlogBean blogBean) {
        Page page=new Page(blogBean.getIndex(),blogBean.getSize());
        IPage<Blog> Page = this.baseMapper.selectPage(page,
                new QueryWrapper<Blog>().lambda()
                        .like(StringUtils.isNotBlank(blogBean.getTitle()),Blog::getTitle,blogBean.getTitle())
                        .like(StringUtils.isNotBlank(blogBean.getTag()),Blog::getTag,blogBean.getTag())
                        .eq(StringUtils.isNotBlank(blogBean.getType()),Blog::getType,blogBean.getType())
                        .orderByDesc(Blog::getCreateTime));
        return TransformationUtil.copyToPage(Page,BlogCO.class);
    }

}

