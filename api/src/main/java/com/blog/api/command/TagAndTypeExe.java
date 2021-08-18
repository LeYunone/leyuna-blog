package com.blog.api.command;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import com.blog.daoservice.dao.TagDao;
import com.blog.daoservice.dao.TypeDao;
import com.blog.daoservice.entry.Tag;
import com.blog.daoservice.entry.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        Page<Type> page=new Page(pageIndex,pageSize);
        int tagsCount=0;
        //如果有模糊查询条件则走模糊查询
        if(StringUtils.isEmpty(conditionName)){
            typeIPage = typeDao.queryByConPage(new Type(), page);
            tagsCount = typeDao.getTagsCount();
        }else{
            typeIPage = typeDao.selectByLikeNamePage(new Type(),page,conditionName);
            tagsCount = typeDao.getTagsCountByLikeName(conditionName);
        }
        //转换结果集
        Page<TypeDTO> result=new Page<>(pageIndex,pageSize);
        result.setTotal(tagsCount);
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
        boolean b = typeDao.updateById(type);
        return b;
    }

    /**
     * 更新标签
     * @param tags
     */
    public boolean updateTags(TagDTO tags){
        Tag tag = TransformationUtil.copyToDTO(tags, Tag.class);
        boolean b = tagDao.updateById(tag);
        return b;
    }
}
