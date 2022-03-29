package com.leyuna.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.TagBean;
import com.leyuna.blog.bean.blog.TypeBean;
import com.leyuna.blog.bean.blog.TypeNavBean;
import com.leyuna.blog.co.blog.TagCO;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.command.TagExe;
import com.leyuna.blog.command.TypeExe;
import com.leyuna.blog.command.TypeNavExe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author pengli
 * @create 2021-08-12 13:24
 *  标签和类型 领域活动对象
 */
@Service
public class TagTypeService {


    @Autowired
    private TagExe tagExe;

    @Autowired
    private TypeExe typeExe;
    
    @Autowired
    private TypeNavExe typeNavExe;
    /**
     * 查询所有的标签  分页 加上 模糊查询
     * @param
     * @return
     */
    public DataResponse<Page<TagCO>> getALlTags(TagBean tag){
        return tagExe.getAllTags(tag);
    }

    /**
     * 查询所有的分类  分页 加上 模糊查询
     * @param
     * @return
     */
    public DataResponse<Page<TypeCO>> getALlTypes(TypeBean type){
        return typeExe.getAllTypes(type);
    }

    /**
     * 添加一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    public DataResponse addTagsAndTypes(List<String> tags, List<String> types, String typeNav){
        if(!CollectionUtils.isEmpty(types)){
            //添加分类
            typeExe.addTypes(types,typeNav);
        }
        //添加标签
        if(!CollectionUtils.isEmpty(tags)){
            tagExe.addTags(tags);
        }
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
        if(!CollectionUtils.isEmpty(types)){
            typeExe.deleteTypes(types);
        }
        if(!CollectionUtils.isEmpty(tags)){
            tagExe.deleteTags(tags);
        }
        return DataResponse.buildSuccess();
    }


    /**
     * 更新一级分类【分类】  或者二级分类【标签】
     * @return
     */
    @Transactional
    public DataResponse updateTypesOrTags(String id,String newName,String name){
        if("type".equals(name)){
            TypeBean typeBean=new TypeBean();
            typeBean.setId(id);
            typeBean.setTypeName(newName);
            typeExe.updateTypes(typeBean);
        }
        if("tag".equals(name)){
            TagBean tagBean=new TagBean();
            tagBean.setId(id);
            tagBean.setTagName(newName);
            tagExe.updateTags(tagBean);
        }
        return DataResponse.buildSuccess();
    }

    /**
     * 得到所有分类导航
     * @return
     */
    public DataResponse<Map<String, TypeNavCO>> getTypeNavMap(TypeNavBean typeNavBean){
        return typeNavExe.getTypeNav(typeNavBean,true);
    }


    /**
     * 得到所有分类导航
     * @return
     */
    public DataResponse getTypeNavList(TypeNavBean typeNavBean){
        return typeNavExe.getTypeNav(typeNavBean,false);
    }

    /**
     * 保存分类导航
     * @param typeNavBean
     * @return
     */
    public DataResponse saveTypeNav(TypeNavBean typeNavBean){
        typeNavExe.saveTypeNav(typeNavBean);
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
