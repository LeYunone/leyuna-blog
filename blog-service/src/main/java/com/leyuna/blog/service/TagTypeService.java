package com.leyuna.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.TagBean;
import com.leyuna.blog.bean.blog.TypeBean;
import com.leyuna.blog.bean.blog.TypeNavBean;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.command.*;
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
    private TagAndTypeExe tagAndTypeExe;

    @Autowired
    private TagExe tagExe;

    @Autowired
    private TypeExe typeExe;
    
    @Autowired
    private TypeNavExe typeNavExe;

    @Autowired
    private CacheExe clearCacheExe;
    /**
     * 查询所有的标签  分页 加上 模糊查询
     * @param
     * @return
     */
    public DataResponse getALlTags(TagBean tag){
        return tagExe.getAllTags(tag);
    }

    /**
     * 查询所有的分类  分页 加上 模糊查询
     * @param
     * @return
     */
    public DataResponse<Page<TypeCO>> getALlTypes(TypeBean type){
        return tagAndTypeExe.getAllTypes(type);
    }

    /**
     * 添加一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    public DataResponse addTypesOrTags(List<String> tags, List<String> types, String typeNav){
        String message=null;

        if(!CollectionUtils.isEmpty(types)){
            //添加分类
            typeExe.addTypes(types,typeNav);
            clearCacheExe.clearTypeQueryCache();
        }
        //添加标签
        if(!CollectionUtils.isEmpty(tags)){
            tagExe.addTags(tags);
            clearCacheExe.clearTagQueryCache();
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
    public DataResponse deleteTypesOrTags(List<String> tags,List<String> types){
        if(!CollectionUtils.isEmpty(types)){
            typeExe.deleteTypes(types);
            clearCacheExe.clearTypeQueryCache();
        }
        if(!CollectionUtils.isEmpty(tags)){
            tagExe.deleteTags(tags);
            clearCacheExe.clearTagQueryCache();
        }
        return DataResponse.buildSuccess();
    }


    /**
     * 更新一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    @Transactional
    public DataResponse updateTypesOrTags(List<TagBean> tags, List<TypeBean> types){
        if(!CollectionUtils.isEmpty(types)){
            typeExe.updateTypes(types);
            clearCacheExe.clearTypeQueryCache();
        }
        if(!CollectionUtils.isEmpty(tags)){
            tagExe.updateTags(tags);
            clearCacheExe.clearTagQueryCache();
        }
        return DataResponse.buildSuccess();
    }

    /**
     * 修改分类导航名
     * @param navName
     * @return
     */
    public DataResponse updateTypeNav(TypeNavBean typeNavBean){
        typeNavExe.updateTypeNav(typeNavBean);
        clearCacheExe.clearTypeNavQueryCache();
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

    public DataResponse addTypeNav(TypeNavBean typeNavBean){
        typeNavExe.updateTypeNav(typeNavBean);
        clearCacheExe.clearTypeNavQueryCache();
        return DataResponse.buildSuccess();
    }

    public DataResponse deleteTypeNav(String typeNavId){
        typeNavExe.deleteTypeNav(typeNavId);
        return DataResponse.buildSuccess();
    }
}
