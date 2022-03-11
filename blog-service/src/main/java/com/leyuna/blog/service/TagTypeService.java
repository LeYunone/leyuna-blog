package com.leyuna.blog.service;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.TagBean;
import com.leyuna.blog.bean.blog.TypeBean;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.command.TagAndTypeExe;
import com.leyuna.blog.command.TagExe;
import com.leyuna.blog.command.TypeExe;
import com.leyuna.blog.domain.TypeNavE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
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
    public DataResponse getALlTypes(TypeBean type){
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
    public DataResponse updateTypeNav(String navName,String typeNavId){
        boolean b = tagAndTypeExe.updateTypeNav(TypeNavE.queryInstance()
                .setTypeNavName(navName).setId(typeNavId));
        if(b){
            clearCacheExe.clearTypeNavQueryCache();
        }
        return b;
    }

    /**
     * 得到所有分类导航
     * @return
     */
    public Map<String, TypeNavCO> getTypeNavMap(){
        List<TypeNavCO> typeNav = tagAndTypeExe.getTypeNav(null);
        Map<String,TypeNavCO> resultMap=new HashMap<>();
        typeNav.stream().forEach(t->{
            resultMap.put(t.getId(),t);
        });
        return resultMap;
    }


    /**
     * 得到所有分类导航
     * @return
     */
    public List<TypeNavCO> getTypeNavList(String conditionName){
        List<TypeNavCO> typeNav = tagAndTypeExe.getTypeNav(conditionName);
        return typeNav;
    }

    public boolean addTypeNav(String navName){
        boolean b = tagAndTypeExe.addTypeNavs(navName);
        if(b){
            clearCacheExe.clearTypeNavQueryCache();
        }
        return b;
    }

    public boolean deleteTypeNav(String typeNavId){
        boolean b = tagAndTypeExe.deleteTypeNav(typeNavId);
        return b;
    }
}
