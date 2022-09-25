package com.leyuna.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.dao.TagDao;
import com.leyuna.blog.dao.repository.entry.TagDO;
import com.leyuna.blog.model.co.TagCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.model.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 13:24
 *  标签和类型 领域活动对象
 */
@Service
public class TagService {

    @Autowired
    private TagDao tagDao;

    /**
     * 查询所有的标签  分页 加上 模糊查询
     * @param
     * @return
     */
    public Page<TagCO> getALlTags(TagDTO tag){
        //如果有模糊查询条件则走模糊查询
        Page<TagCO> tagPage = tagDao.queryTag(tag);
        List<TagCO> records = tagPage.getRecords();
        records.stream().forEach(t -> {
            LocalDateTime lastTime = t.getUpdateDt();
            //如果最后使用的时间加了一个月还在现在的时间前面，那么就说明这个标签很久没用了
            if (LocalDateTime.now().isBefore(lastTime.plusMonths(1))) {
                t.setUserStatus("hot");
            } else {
                t.setUserStatus("cold");
            }
        });
        Collections.sort(records);
        return tagPage;
    }

    /**
     * 添加一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    public DataResponse addTags(List<String> tags){
        //添加标签
        List<TagDO> tagDOS = new ArrayList<>();
        tags.stream().forEach(t->{
            TagDO tagDO = new TagDO();
            tagDO.setTagName(t);
            tagDO.setUseCount(0);
            tagDOS.add(tagDO);
        });
        tagDao.insertOrUpdateBatch(tagDOS);
        return DataResponse.buildSuccess();
    }

    /**
     * 删除一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    @Transactional
    public DataResponse deleteTags(List<String> tags){
        tagDao.deleteByIdBatch(tags);
        return DataResponse.buildSuccess();
    }


    /**
     * 更新一级分类【分类】  或者二级分类【标签】
     * @return
     */
    @Transactional
    public DataResponse updateTag(TagDTO tagDTO){
        tagDao.insertOrUpdate(tagDTO);
        return DataResponse.buildSuccess();
    }
}
