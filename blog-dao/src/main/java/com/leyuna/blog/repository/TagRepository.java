package com.leyuna.blog.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.TagCO;
import com.leyuna.blog.domain.TagE;
import com.leyuna.blog.entry.Tag;
import com.leyuna.blog.error.ErrorMessage;
import com.leyuna.blog.gateway.TagGateway;
import com.leyuna.blog.repository.mapper.TagMapper;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.ObjectUtil;
import com.leyuna.blog.util.TransformationUtil;
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
     * @param page
     * @return
     */
    @Override
    public Page<TagCO> selectByLikeNamePage(TagE tag, Integer index, Integer size, String conditionName) {
        AssertUtil.isTrue(ObjectUtil.isNotNull (tag), ErrorMessage.OBJECT_NULL);
        Page page=new Page(index,size);
        Map<String, Object> stringObjectMap = TransformationUtil.transDTOColumnMap(tag);
        IPage<Tag> Page = this.baseMapper.selectPage(page, new QueryWrapper<Tag>()
                .allEq(stringObjectMap).like("tag_Name",conditionName));
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
            boolean update = this.update(new UpdateWrapper<Tag>().lambda().eq(Tag::getTagName, name).set(Tag::getLastUserTime, LocalDateTime.now()));
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
