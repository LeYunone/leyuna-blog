package com.leyuna.blog.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.core.dao.TagDao;
import com.leyuna.blog.core.dao.TypeDao;
import com.leyuna.blog.core.model.co.TagCO;
import com.leyuna.blog.core.model.co.TypeCO;
import com.leyuna.blog.core.model.co.TypeNavCO;
import com.leyuna.blog.core.model.constant.DataResponse;
import com.leyuna.blog.core.model.dto.TagDTO;
import com.leyuna.blog.core.model.dto.TypeDTO;
import com.leyuna.blog.core.model.dto.TypeNavDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 13:24
 *  标签和类型 领域活动对象
 */
@Service
public class TagTypeService {
    
    @Autowired
    private TagDao tagDao;
    @Autowired
    private TypeDao typeDao;
    
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
     * 查询所有的分类  分页 加上 模糊查询
     * @param
     * @return
     */
    public DataResponse<Page<TypeCO>> getALlTypes(TypeDTO type){
        //如果有模糊查询条件则走模糊查询
        Page<TypeCO> typePage = typeDao.queryType(type);
        typePage.getRecords().stream().forEach(t -> {
            LocalDateTime lastTime = t.getUpdateDt();
            //如果最后使用的时间加了一个月还在现在的时间前面，那么就说明这个标签很久没用了
            if (LocalDateTime.now().isBefore(lastTime.plusMonths(1))) {
                t.setUserStatus("hot");
            } else {
                t.setUserStatus("cold");
            }
        });

        return DataResponse.of(typePage);
    }

    /**
     * 添加一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    public DataResponse addTagsAndTypes(List<String> tags, List<String> types, String typeNav){
//        if(!CollectionUtils.isEmpty(types)){
//            //添加分类
//            typeExe.addTypes(types,typeNav);
//        }
//        //添加标签
//        if(!CollectionUtils.isEmpty(tags)){
//            tagExe.addTags(tags);
//        }
        return DataResponse.buildSuccess();
    }

    /**
     * 删除一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    @Transactional
    public DataResponse deleteTagsAndTypes(List<String> tags,List<String> types){
//        if(!CollectionUtils.isEmpty(types)){
//            typeExe.deleteTypes(types);
//        }
//        if(!CollectionUtils.isEmpty(tags)){
//            tagExe.deleteTags(tags);
//        }
        return DataResponse.buildSuccess();
    }


    /**
     * 更新一级分类【分类】  或者二级分类【标签】
     * @return
     */
    @Transactional
    public DataResponse updateTypesOrTags(String id,String newName,String name){
//        if("type".equals(name)){
//            TypeDTO typeDTO =new TypeDTO();
//            typeDTO.setId(id);
//            typeDTO.setTypeName(newName);
//            typeExe.updateTypes(typeDTO);
//        }
//        if("tag".equals(name)){
//            TagDTO tagDTO =new TagDTO();
//            tagDTO.setId(id);
//            tagDTO.setTagName(newName);
//            tagExe.updateTags(tagDTO);
//        }
        return DataResponse.buildSuccess();
    }

    /**
     * 得到所有分类导航
     * @return
     */
    public DataResponse<Map<String, TypeNavCO>> getTypeNavMap(TypeNavDTO typeNavDTO){
        return typeNavExe.getTypeNav(typeNavDTO,true);
    }


    /**
     * 得到所有分类导航
     * @return
     */
    public DataResponse getTypeNavList(TypeNavDTO typeNavDTO){
        return typeNavExe.getTypeNav(typeNavDTO,false);
    }

    /**
     * 保存分类导航
     * @param typeNavDTO
     * @return
     */
    public DataResponse saveTypeNav(TypeNavDTO typeNavDTO){
        typeNavExe.saveTypeNav(typeNavDTO);
        return DataResponse.buildSuccess();
    }

    /**
     * 删除分类导航
     * @param typeNavId
     * @return
     */
    public DataResponse deleteTypeNav(String typeNavId){
        typeNavExe.deleteTypeNav(typeNavId);
        return DataResponse.buildSuccess();
    }
}
