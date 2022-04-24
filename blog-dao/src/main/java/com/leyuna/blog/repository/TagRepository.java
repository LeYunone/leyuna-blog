package com.leyuna.blog.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.TagBean;
import com.leyuna.blog.co.blog.TagCO;
import com.leyuna.blog.gateway.TagGateway;
import com.leyuna.blog.repository.entry.TagDO;
import com.leyuna.blog.repository.mapper.TagMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TagRepository extends BaseRepository<TagMapper, TagDO, TagCO> implements TagGateway {

    /**
     * 定制查询
     * @param
     * @param
     * @return
     */
    @Override
    public Page<TagCO> selectByCon(TagBean tag) {
        Page page=new Page(tag.getIndex(),tag.getSize());
        LambdaQueryWrapper<TagDO> like = new QueryWrapper<TagDO>().lambda()
                .like(StringUtils.isNotBlank(tag.getTagName()), TagDO::getTagName, tag.getTagName());
        IPage<TagDO> Page = this.baseMapper.selectPage(page,like);
        return TransformationUtil.copyToPage(Page,TagCO.class);
    }
}
