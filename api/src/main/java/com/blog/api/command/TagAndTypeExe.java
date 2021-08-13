package com.blog.api.command;

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
     * 获取所有标签
     * @return
     */
    public List<TagDTO> getAllTags(){
        List<Tag> list = tagDao.queryByCon(new Tag());
        List<TagDTO> copyList = TransformationUtil.copyToLists(list, TagDTO.class);
        return copyList;
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
     * 获取所有分类
     * @return
     */
    public List<TypeDTO> getAllTypes(){
        List<Type> list = typeDao.queryByCon(new Type());
        List<TypeDTO> copyList = TransformationUtil.copyToLists(list, TypeDTO.class);
        return copyList;
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
    public boolean updateTypes(List<TypeDTO> types){
        List<Type> copyList = TransformationUtil.copyToLists(types, Type.class);
        boolean b = typeDao.updateBatchById(copyList);
        return b;
    }

    /**
     * 更新标签
     * @param tags
     */
    public boolean updateTags(List<TagDTO> tags){
        List<Tag> copyList = TransformationUtil.copyToLists(tags, Tag.class);
        boolean b = tagDao.updateBatchById(copyList);
        return b;
    }
}
