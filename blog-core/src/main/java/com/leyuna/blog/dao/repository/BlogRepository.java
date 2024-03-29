package com.leyuna.blog.dao.repository;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.dao.BlogDao;
import com.leyuna.blog.dao.repository.entry.BlogDO;
import com.leyuna.blog.dao.repository.mapper.BlogMapper;
import com.leyuna.blog.model.co.BlogCO;
import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.model.query.BlogQuery;
import com.leyuna.blog.util.TransformationUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (BlogDO)原子服务
 *
 * @author pengli
 * @since 2021-08-13 15:38:37
 */
@Repository
public class BlogRepository extends BaseRepository<BlogMapper, BlogDO> implements BlogDao {

    @Override
    public Page<BlogCO> queryBlog(BlogDTO blog) {
        Page page=new Page(blog.getIndex(),blog.getSize());
        IPage<BlogDO> Page = this.baseMapper.selectPage(page,
                new QueryWrapper<BlogDO>().lambda()
                        .like(StrUtil.isNotBlank(blog.getTitle()),BlogDO::getTitle,blog.getTitle())
                        .like(StrUtil.isNotBlank(blog.getTag()),BlogDO::getTag,blog.getTag())
                        .in(null!=blog.getBlogType(),BlogDO::getBlogType,blog.getBlogType())
                        .orderByDesc(BlogDO::getCreateDt));
        return TransformationUtil.copyToPage(Page, BlogCO.class);
    }

    @Override
    public List<BlogCO> selectRandomList () {
        return this.baseMapper.selectRandomList();
    }

    /**
     * 查询顶级菜单下的博客
     * @return
     */
    @Override
    public IPage<BlogDO> selectByMenuTopOrderTime(BlogQuery blogQuery) {
        LambdaQueryWrapper<BlogDO> lambda = new QueryWrapper<BlogDO>().lambda();
        lambda.in(BlogDO::getMenuId,blogQuery.getMenuTopIds());
        lambda.gt(ObjectUtil.isNotNull(blogQuery.getCreateDate()),BlogDO::getCreateDt,blogQuery.getCreateDate());
        lambda.orderByDesc(BlogDO::getCreateDt);
        Page page = new Page(blogQuery.getIndex(),blogQuery.getSize());
        return this.baseMapper.selectPage(page,lambda);
    }

    @Override
    public List<BlogDO> selectLeetCode() {
        LambdaQueryWrapper<BlogDO> lambda = new QueryWrapper<BlogDO>().lambda();
        lambda.like(BlogDO::getTitle,"LeetCode");
        lambda.orderByAsc(BlogDO::getTitle);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public List<BlogDO> selectNormalNotes() {
        LambdaQueryWrapper<BlogDO> lambda = new QueryWrapper<BlogDO>().lambda();
        lambda.eq(BlogDO::getMenuId,5);
        return this.baseMapper.selectList(lambda);
    }
}

