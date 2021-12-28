package com.leyuna.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.blog.TagCO;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.command.TagAndTypeExe;
import com.leyuna.blog.domain.TagE;
import com.leyuna.blog.domain.TypeE;
import com.leyuna.blog.domain.TypeNavE;
import com.leyuna.blog.error.SystemAsserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author pengli
 * @create 2021-08-12 13:24
 *  标签和类型 领域活动对象
 */
@Service
public class TagTypeDomain {

    @Autowired
    private TagAndTypeExe tagAndTypeExe;

    @Autowired
    private CacheExe clearCacheExe;
    /**
     * 根据id查询标签
     * @param ids
     * @return
     */
    public List<TagCO> getTagsByIds(String ... ids){
        List<TagCO> allTag=null;
        //独立查询
        allTag=tagAndTypeExe.getTagByIds(Arrays.asList(ids));
        allTag.stream().forEach(tag->{
            LocalDateTime lastTime=tag.getLastUserTime();
            //如果最后使用的时间加了一个月还在现在的时间前面，那么就说明这个标签很久没用了
            if(LocalDateTime.now().isBefore(lastTime.plusMonths(1))){
                tag.setUserStatus("hot");
            }else{
                tag.setUserStatus("cold");
            }
        });
        return allTag;
    }

    /**
     * 根据id查询分类
     * @param ids
     * @return
     */
    public List<TypeCO> getTypesByIds(String ... ids){
        List<TypeCO> allType=null;
        //独立查询
        allType=tagAndTypeExe.getTypeByIds(Arrays.asList(ids));
        allType.stream().forEach(type->{
            LocalDateTime lastTime=type.getLastUserTime();
            //如果最后使用的时间加了一个月还在现在的时间前面，那么就说明这个标签很久没用了
            if(LocalDateTime.now().isBefore(lastTime.plusMonths(1))){
                type.setUserStatus("hot");
            }else{
                type.setUserStatus("cold");
            }
        });
        return allType;
    }

    /**
     * 查询所有的标签  分页 加上 模糊查询
     * @param
     * @return
     */
    public Page<TagCO> getALlTags(Integer pageIndex, Integer pageSize, String conditionName){
        Page<TagCO> Page=null;
        if(pageIndex!=null){
            Page = tagAndTypeExe.getAllTags(pageIndex,pageSize,conditionName);
            Page.getRecords().stream().forEach(tag->{
                LocalDateTime lastTime=tag.getLastUserTime();
                //如果最后使用的时间加了一个月还在现在的时间前面，那么就说明这个标签很久没用了
                if(LocalDateTime.now().isBefore(lastTime.plusMonths(1))){
                    tag.setUserStatus("hot");
                }else{
                    tag.setUserStatus("cold");
                }
            });
        }else{
            Page = tagAndTypeExe.getAllTags(1,100,conditionName);
        }
        Collections.sort(Page.getRecords());
        return Page;
    }

    /**
     * 查询所有的分类  分页 加上 模糊查询
     * @param
     * @return
     */
    public Page<TypeCO> getALlTypes(Integer pageIndex,Integer pageSize,String conditionName){
        Page<TypeCO> Page = tagAndTypeExe.getAllTypes(pageIndex,pageSize,conditionName);
        Page.getRecords().stream().forEach(tag->{
            LocalDateTime lastTime=tag.getLastUserTime();
            //如果最后使用的时间加了一个月还在现在的时间前面，那么就说明这个标签很久没用了
            if(LocalDateTime.now().isBefore(lastTime.plusMonths(1))){
                tag.setUserStatus("hot");
            }else{
                tag.setUserStatus("cold");
            }
        });
        return Page;
    }

    /**
     * 添加一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    public String addTypesOrTags(List<String> tags, List<String> types, String typeNav){
        String message=null;
        //添加分类
        if(!CollectionUtils.isEmpty(types)){
            List<TypeE> listTypes=new ArrayList<>();
            //将名字封装成类
            types.stream().forEach(type->{
                TypeE typeDTOBuilder = TypeE.queryInstance().setTypeName(type).
                        setCreateTime(LocalDateTime.now()).
                        setLastUserTime(LocalDateTime.now()).setUseCount(0).setFatherType(typeNav);
                listTypes.add(typeDTOBuilder);
            });
            boolean b = tagAndTypeExe.addTypes(listTypes);
            if(!b){
                message=SystemAsserts.ADD_TYPE_FALE.getMsg();
            }else{
                clearCacheExe.clearTypeQueryCache();
            }
        }
        //添加标签
        if(!CollectionUtils.isEmpty(tags)){
            List<TagE> listTags=new ArrayList<>();
            //将名字封装成类
            tags.stream().forEach(tag->{
                TagE tagDTO = TagE.queryInstance().
                        setTagName(tag).setCreateTime(LocalDateTime.now()).
                        setLastUserTime(LocalDateTime.now()).setUseCount(0);
                listTags.add(tagDTO);
            });
            boolean b = tagAndTypeExe.addTags(listTags);
            if(!b){
                message=SystemAsserts.ADD_TAG_FALE.getMsg();
            }else{
                clearCacheExe.clearTagQueryCache();
            }
        }
        return message;
    }

    /**
     * 删除一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    @Transactional
    public String deleteTypesOrTags(List<String> tags,List<String> types){
        String message =null;
        try {
            if(!CollectionUtils.isEmpty(types)){
                tagAndTypeExe.deleteTypes(types);
                clearCacheExe.clearTypeQueryCache();
            }
        }catch (Exception e){
            message = SystemAsserts.DELETE_TYPE_FALE.getMsg() ;
        }
        try {
            if(!CollectionUtils.isEmpty(tags)){
                tagAndTypeExe.deleteTags(tags);
                clearCacheExe.clearTagQueryCache();
            }
        }catch (Exception e) {
            message = SystemAsserts.DELETE_TAG_FALE.getMsg();
        }
        return message;
    }


    /**
     * 更新一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    @Transactional
    public String updateTypesOrTags(TagE tags, TypeE types){
        String message=null;
        if(null!=types){
            boolean b = tagAndTypeExe.updateTypes(types);
            if(!b){
                message = SystemAsserts.UPDATE_TYPE_FALE.getMsg();
            }else{
                clearCacheExe.clearTypeQueryCache();
            }
        }
        if(null!=tags){
            boolean b = tagAndTypeExe.updateTags(tags);
            if(!b){
                message = SystemAsserts.UPDATE_TAG_FALE.getMsg();
            }else{
                clearCacheExe.clearTagQueryCache();
            }
        }
        return message;
    }

    /**
     * 修改分类导航名
     * @param navName
     * @return
     */
    public boolean updateTypeNav(String navName,String typeNavId){
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
