package com.leyuna.blog.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.TagBean;
import com.leyuna.blog.co.blog.TagCO;
import com.leyuna.blog.gateway.TagGateway;
import com.leyuna.blog.repository.mapper.TagMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author pengli
 * @create 2021-08-10 14:49
 *  user表原子对象
 */
@Service
public class TagRepository extends BaseRepository<TagMapper,Tag, TagCO> implements TagGateway {

    /**
     * 分页模糊查询
     * @param
     * @param
     * @return
     */
    @Override
    public Page<TagCO> selectLikePage(TagBean tag) {
        Page page=new Page(tag.getIndex(),tag.getSize());
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(tag);
        IPage<Tag> Page = this.baseMapper.selectPage(page, new QueryWrapper<Tag>()
                .allEq(stringObjectMap)
                .like(StringUtils.isNotBlank(tag.getConditionName()),"tag_Name",tag.getConditionName()));
        return TransformationUtil.copyToPage(Page,TagCO.class);
    }

    @Override
    public int getTagsCount(){
        int count = this.count();
        return count;
    }

    @Override
    public int getTagsCountByLikeName(String conditionName) {
        return this.count(new QueryWrapper<Tag>().lambda().like(Tag::getTagName,conditionName));
    }

    @Override
    public boolean updateLastUseTimeByName(String[] names) {
        boolean is=true;
        for(String name:names){
            boolean update = this.update(new UpdateWrapper<Tag>().lambda().eq(Tag::getTagName, name).set(Tag::getupdateDt, LocalDateTime.now()));
            is=update;
            if(!is){
                break;
            }
        }
        return is;
    }

    @Override
    public boolean updateUseCountByName(String name,int userCount){
        boolean update = this.update(new UpdateWrapper<Tag>().lambda().eq(Tag::getTagName, name).set(Tag::getUseCount, userCount));
        return update;
    }
}
