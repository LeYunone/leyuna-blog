package com.blog.api.command;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import com.blog.api.dto.TypeNavDTO;
import com.blog.daoservice.dao.TagDao;
import com.blog.daoservice.dao.TypeDao;
import com.blog.daoservice.dao.TypeNavDao;
import com.blog.daoservice.entry.Tag;
import com.blog.daoservice.entry.Type;
import com.blog.daoservice.entry.TypeNav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.CollectionUtil;
import util.TransformationUtil;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 13:29
 *
 *  标签以及分类 内容服务
 */
@Service
public class TagAndTypeExe {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private TypeNavDao typeNavDao;

    /**
     * 获取所有标签  功能属于 分页 模糊
     * @return
     */
    public Page<TagDTO> getAllTags(Integer pageIndex,Integer pageSize,String conditionName){
        IPage<Tag> tagIPage=null;
        Page<Tag> page=new Page(pageIndex,pageSize);
        //如果有模糊查询条件则走模糊查询
        if(StringUtils.isEmpty(conditionName)){
            tagIPage = tagDao.queryByConPage(new Tag(), page);
        }else{
            tagIPage = tagDao.selectByLikeNamePage(new Tag(),page,conditionName);
        }
        //转换结果集
        Page<TagDTO> result=new Page<>(pageIndex,pageSize);
        result.setTotal(tagIPage.getTotal());
        result.setRecords( TransformationUtil.copyToLists(tagIPage.getRecords(),TagDTO.class));
        return result;
    }

    /**
     *   根据ids 获取标签
     * @param ids
     * @return
     */
    public List<TagDTO> getTagByIds(List<Integer> ids){
        List<Tag> tags = tagDao.selectByIds(ids);
        List<TagDTO> copyList = TransformationUtil.copyToLists(tags, TagDTO.class);
        return copyList;
    }

    /**
     * 获取所有分类  有分页和模糊
     * @return
     */
    public Page<TypeDTO> getAllTypes(Integer pageIndex,Integer pageSize,String conditionName){
        IPage<Type> typeIPage=null;
        Page<Type> page=null;
        if(pageIndex==null&&pageIndex==null){
            pageIndex=1;
            //暂时限定数据库中只会存在100条分类
            pageSize=100;
            page=new Page(pageIndex,pageSize);
        }else{
            page=new Page(pageIndex,pageSize);
        }
        //如果有模糊查询条件则走模糊查询
        if(StringUtils.isEmpty(conditionName)){
            typeIPage = typeDao.queryByConPage(new Type(), page);
        }else{
            typeIPage = typeDao.selectByLikeNamePage(new Type(),page,conditionName);
        }
        //转换结果集
        Page<TypeDTO> result=new Page<>(pageIndex,pageSize);
        result.setTotal(typeIPage.getTotal());
        result.setRecords( TransformationUtil.copyToLists(typeIPage.getRecords(),TypeDTO.class));
        return result;
    }

    /**
     * 根据ids获取分类
     * @param ids
     * @return
     */
    public List<TypeDTO> getTypeByIds(List<Integer> ids){
        List<Type> tags = typeDao.selectByIds(ids);
        List<TypeDTO> copyList = TransformationUtil.copyToLists(tags, TypeDTO.class);
        return copyList;
    }

    /**
     * 添加分类
     * @param types
     * @return
     */
    public boolean addTypes(List<TypeDTO> types){
        List<Type> copyList = TransformationUtil.copyToLists(types, Type.class);
        boolean b = typeDao.saveBatch(copyList);
        return b;
    }

    /**
     * 添加标签
     * @param tags
     * @return
     */
    public boolean addTags(List<TagDTO> tags){
        List<Tag> copyList = TransformationUtil.copyToLists(tags, Tag.class);
        boolean b = tagDao.saveBatch(copyList);
        return b;
    }

    public boolean addTypeNavs(String navName){
        boolean b = typeNavDao.save(TypeNav.builder().typeNavName(navName).build());
        return b;
    }

    /**
     * 删除分类
     * @param types
     */
    @Transactional
    public void deleteTypes(List<Integer> types){
        int i = typeDao.deleteTypesByIds(types);
        if(i!=types.size()){
            //抛出异常使之回滚
            throw new RuntimeException();
        }
    }

    /**
     * 删除标签
     * @param tags
     */
    @Transactional
    public void deleteTags(List<Integer> tags){
        int i = tagDao.deleteTagsByIds(tags);
        if(i!=tags.size()){
            //抛出异常使之回滚
            throw new RuntimeException();
        }
    }

    /**
     * 更新分类
     * @param types
     */
    public boolean updateTypes(TypeDTO types){
        Type type = TransformationUtil.copyToDTO(types, Type.class);
        boolean b = typeDao.updateNameById(type);
        return b;
    }

    /**
     * 更新标签
     * @param tags
     */
    public boolean updateTags(TagDTO tags){
        Tag tag = TransformationUtil.copyToDTO(tags, Tag.class);
        boolean b = tagDao.updateNameById(tag);
        return b;
    }

    public boolean updateTypeNav(TypeNavDTO typeNavDTO){
        TypeNav typeNav = TransformationUtil.copyToDTO(typeNavDTO, TypeNav.class);
        boolean b = typeNavDao.updateById(typeNav);
        return b;
    }

    /**
     * 更新标签最后使用时间 根据名称批量
     */
    public boolean updateTags(String [] names){
        boolean b = tagDao.updateLastUseTimeByName(names);
        return b;
    }

    /**
     * 更新分类最后使用时间 根据id
     */
    public boolean updateTypes(Integer id){
        boolean b = typeDao.updateLastUseTimeById(id);
        return b;
    }

    /**
     * 判断是否可以根据名字查到标签
     * @return
     */
    public boolean getTagByName(String name){
        List<Tag> tags = tagDao.queryByCon(Tag.builder().tagName(name).build());
        if(CollectionUtils.isNotEmpty(tags)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断是否可以根据名字查到标签
     * @return
     */
    public boolean addTagUseCountByName(String name){
        List<Tag> tags = tagDao.queryByCon(Tag.builder().tagName(name).build());
        Tag first = CollectionUtil.getFirst(tags);
        Integer useCount = first.getUseCount();
        boolean b = tagDao.updateUseCountByName(name, useCount);
        return b;
    }

    public boolean updateTagsAndTypes(String tagNames,Integer typeId){
        boolean tagTupdate=true;
        if(StringUtils.isNotEmpty(tagNames)){
            String[] names=tagNames.split(",");
            tagTupdate= tagDao.updateLastUseTimeByName(names);
        }
        boolean typeUpdate= typeDao.updateLastUseTimeById(typeId);
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
    public List<TypeNavDTO> getTypeNav(String conditionName){
        List<TypeNav> typeNavs =null;
        if(StringUtils.isNotEmpty(conditionName)){
            typeNavs=typeNavDao.queryAllTypeNavConditionName(conditionName);
        }else{
            typeNavs=typeNavDao.queryByCon(new TypeNav());
        }
        return TransformationUtil.copyToLists(typeNavs,TypeNavDTO.class);
    }

    public boolean deleteTypeNav(Integer typeNavId){
        boolean b = typeNavDao.removeById(typeNavId);
        return b;
    }
}
