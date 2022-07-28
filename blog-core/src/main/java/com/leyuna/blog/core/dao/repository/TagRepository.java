package com.leyuna.blog.core.dao.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.core.dao.TagDao;
import com.leyuna.blog.core.dao.repository.entry.TagDO;
import com.leyuna.blog.core.dao.repository.mapper.TagMapper;
import com.leyuna.blog.core.model.co.TagCO;
import com.leyuna.blog.core.model.dto.TagDTO;
import com.leyuna.blog.core.util.TransformationUtil;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TagRepository extends BaseRepository<TagMapper, TagDO> implements TagDao {

    @Override
    public Page<TagCO> queryTag(TagDTO tag) {
        Page page=new Page(tag.getIndex(),tag.getSize());
        LambdaQueryWrapper<TagDO> like = new QueryWrapper<TagDO>().lambda()
                .like(StrUtil.isNotBlank(tag.getTagName()), TagDO::getTagName, tag.getTagName());
        IPage<TagDO> Page = this.baseMapper.selectPage(page,like);
        return TransformationUtil.copyToPage(Page,TagCO.class);
    }
}
