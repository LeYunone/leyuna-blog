package com.leyuna.blog.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.BlogCO;
import com.leyuna.blog.entry.Blog;
import com.leyuna.blog.gateway.BlogGateway;
import com.leyuna.blog.repository.mapper.BlogMapper;
import com.leyuna.blog.util.TransformationUtil;
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
    public IPage<BlogCO> queryByTagName (BlogCO e, Integer index , Integer size) {
        Page page=new Page(index,size);
        IPage<Blog> iPage = this.baseMapper.selectPage(page, new QueryWrapper<Blog>().lambda().like(Blog::getTag,e.getTag()).orderByDesc(Blog::getCreateTime));
        return TransformationUtil.copyToPage(iPage,BlogCO.class);
    }

    @Override
    public int queryCountByType(Integer type){
        return this.count(new QueryWrapper<Blog>().lambda().eq(Blog::getType,type));
    }

    @Override
    public boolean updateClickCount(String blogId,Integer clickCount){
        return this.update(new UpdateWrapper<Blog>().lambda().eq(Blog::getId,blogId).set(Blog::getClickCount,clickCount));
    }

    @Override
    public IPage<BlogCO> queryByBlogName(String title, Integer index, Integer size) {
        Page page=new Page(index,size);
        IPage<Blog> iPage = this.baseMapper.selectPage(page,
                new QueryWrapper<Blog>().lambda().like(Blog::getTitle,title).orderByDesc(Blog::getCreateTime));
        return TransformationUtil.copyToPage(iPage,BlogCO.class);
    }

}

