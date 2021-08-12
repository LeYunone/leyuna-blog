package com.blog.api.command;

import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import com.blog.daoservice.dao.TagDao;
import com.blog.daoservice.dao.TypeDao;
import com.blog.daoservice.entry.Tag;
import com.blog.daoservice.entry.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<TagDTO> getAllTags(){
        List<Tag> list = tagDao.queryByCon(new Tag());
        List<TagDTO> copyList = TransformationUtil.copyToLists(list, TagDTO.class);
        return copyList;
    }

    public List<TagDTO> getTagByIds(List<Integer> ids){
        List<Tag> tags = tagDao.selectByIds(ids);
        List<TagDTO> copyList = TransformationUtil.copyToLists(tags, TagDTO.class);
        return copyList;
    }

    public List<TypeDTO> getAllTypes(){
        List<Type> list = typeDao.queryByCon(new Type());
        List<TypeDTO> copyList = TransformationUtil.copyToLists(list, TypeDTO.class);
        return copyList;
    }

    public List<TypeDTO> getTypeByIds(List<Integer> ids){
        List<Type> tags = typeDao.selectByIds(ids);
        List<TypeDTO> copyList = TransformationUtil.copyToLists(tags, TypeDTO.class);
        return copyList;
    }
}
