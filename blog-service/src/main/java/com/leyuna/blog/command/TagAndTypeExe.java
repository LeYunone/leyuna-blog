package com.leyuna.blog.command;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyuna.blog.co.TagCO;
import com.leyuna.blog.co.TypeCO;
import com.leyuna.blog.co.TypeNavCO;
import com.leyuna.blog.domain.TagE;
import com.leyuna.blog.domain.TypeE;
import com.leyuna.blog.domain.TypeNavE;
import com.leyuna.blog.util.CollectionUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 13:29
 *
 *  标签以及分类 内容服务
 */
@Service
public class TagAndTypeExe {


    /**
     * 获取所有标签  功能属于 分页 模糊
     * @return
     */
    @Cacheable(cacheNames = "getAllTags")
    public IPage<TagCO> getAllTags(Integer pageIndex, Integer pageSize, String conditionName){
        IPage<TagCO> tagIPage=null;
        //如果有模糊查询条件则走模糊查询
        if(StringUtils.isEmpty(conditionName)){
            tagIPage = TagE.queryInstance().getGateway().selectByPage(TagE.queryInstance(), pageIndex,pageSize);
        }else{
            tagIPage = TagE.queryInstance().getGateway().selectByLikeNamePage(TagE.queryInstance(),pageIndex,pageSize,conditionName);
        }
        return tagIPage;
    }

    /**
     *   根据ids 获取标签
     * @param ids
     * @return
     */
    @Cacheable(cacheNames = "getTagByIds")
    public List<TagCO> getTagByIds(List<String> ids){

        List<TagCO> tags = TagE.queryInstance().getGateway().selectByIds(ids);
        return tags;
    }

    /**
     * 获取所有分类  有分页和模糊
     * @return
     */
    @Cacheable(cacheNames = "getAllTypes")
    public IPage<TypeCO> getAllTypes(Integer pageIndex, Integer pageSize, String conditionName){
        IPage<TypeCO> typeIPage=null;
        if(pageIndex==null&&pageIndex==null){
            pageIndex=1;
            //暂时限定数据库中只会存在100条分类
            pageSize=100;
        }else{
        }
        //如果有模糊查询条件则走模糊查询
        if(StringUtils.isEmpty(conditionName)){
            typeIPage = TypeE.queryInstance().getGateway().selectByPage(TypeE.queryInstance(), pageIndex,pageSize);
        }else{
            typeIPage = TypeE.queryInstance().getGateway().selectByLikeNamePage(TypeE.queryInstance(),pageIndex,pageSize,conditionName);
        }
        return typeIPage;
    }

    /**
     * 根据ids获取分类
     * @param ids
     * @return
     */
    @Cacheable(cacheNames = "getTypeByIds")
    public List<TypeCO> getTypeByIds(List<String> ids){
        List<TypeCO> typeCOS = TypeE.queryInstance().getGateway().selectByIds(ids);
        return typeCOS;
    }

    /**
     * 添加分类
     * @param types
     * @return
     */
    public boolean addTypes(List<TypeE> types){
        boolean b = TypeE.batchCreate(types);
        return b;
    }

    /**
     * 添加标签
     * @param tags
     * @return
     */
    public boolean addTags(List<TagE> tags){
        boolean b = TagE.batchCreate(tags);
        return b;
    }

    public boolean addTypeNavs(String navName){
        TypeNavCO save = TypeNavE.queryInstance().setTypeNavName(navName).save();
        return save!=null;
    }

    /**
     * 删除分类
     * @param types
     */
    @Transactional
    public void deleteTypes(List<String> types){
        int b = TypeE.queryInstance().getGateway().batchDelete(types);
        if(b!=types.size()){
            //抛出异常使之回滚
            throw new RuntimeException();
        }
    }

    /**
     * 删除标签
     * @param tags
     */
    @Transactional
    public void deleteTags(List<String> tags){
        int b = TagE.queryInstance().getGateway().batchDelete(tags);
        if(b!=tags.size()){
            //抛出异常使之回滚
            throw new RuntimeException();
        }
    }

    /**
     * 更新分类
     * @param types
     */
    public boolean updateTypes(TypeE types){
        boolean update = types.update();
        return update;
    }

    /**
     * 更新标签
     * @param tags
     */
    public boolean updateTags(TagE tags){
        boolean update = tags.update();
        return update;
    }

    public boolean updateTypeNav(TypeNavE typeNavDTO){
        boolean b = typeNavDTO.update();
        return b;
    }

    /**
     * 判断是否可以根据名字查到标签
     * @return
     */
    public boolean getTagByName(String name){
        List<TagCO> tags = TagE.queryInstance().setTagName(name).selectByCon();
        if(CollectionUtils.isNotEmpty(tags)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 添加标签使用次数
     * @return
     */
    public boolean addTagUseCountByName(String name){
        List<TagCO> tags = TagE.queryInstance().setTagName(name).selectByCon();
        TagCO first = CollectionUtil.getFirst(tags);
        Integer useCount = first.getUseCount();
        boolean b = TagE.queryInstance().getGateway().updateUseCountByName(name, useCount+1);
        return b;
    }

    /**
     * 添加分类使用次数
     * @return
     */
    public boolean addTypeUseCount(String id){
        TypeCO type = TypeE.queryInstance().setId(id).selectById();
        Integer useCount=type.getUseCount();
        boolean b = TypeE.queryInstance().getGateway().updateUseCountByName(id, useCount + 1);
        return b;
    }

    public boolean updateTagsAndTypes(String tagNames,String typeId){
        boolean tagTupdate=true;
        if(StringUtils.isNotEmpty(tagNames)){
            String[] names=tagNames.split(",");
            tagTupdate= TagE.queryInstance().getGateway().updateLastUseTimeByName(names);
        }
        boolean typeUpdate= TypeE.queryInstance().getGateway().updateLastUseTimeById(typeId);
        if(tagTupdate && typeUpdate){
            return true;
        }else{
            //事务回滚
            throw new RuntimeException();
        }
    }

    /**
     * 得到分类导航
     * @return
     */
    @Cacheable(cacheNames = "getTypeNav")
    public List<TypeNavCO> getTypeNav(String conditionName){
        List<TypeNavCO> typeNavs =null;
        if(StringUtils.isNotEmpty(conditionName)){
            typeNavs=TypeNavE.queryInstance().getGateway().queryAllTypeNavConditionName(conditionName);
        }else{
            typeNavs=TypeNavE.queryInstance().selectByCon();
        }
        return typeNavs;
    }

    public boolean deleteTypeNav(String typeNavId){
        int delete = TypeNavE.queryInstance().getGateway().delete(typeNavId);
        return delete!=0;
    }
}
