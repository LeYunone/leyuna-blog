package com.blog.api.domain;

import com.blog.api.command.TagAndTypeExe;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 13:24
 *  标签和类型 领域活动对象
 */
@Service
public class TagTypeDomain {

    @Autowired
    private TagAndTypeExe tagAndTypeExe;

    /**
     * 查询所有的标签
     * @param ids  根据业务需求如果需要根据id查询则返回正常查询值
     * @return
     */
    public List<TagDTO> getALlTags(Integer ... ids){
        List<TagDTO> allTag=null;
        if(ids.length==0){
            allTag = tagAndTypeExe.getAllTags();
        }else{
            allTag=tagAndTypeExe.getTagByIds(Arrays.asList(ids));
        }
        return allTag;
    }

    /**
     * 查询所有的分类
     * @param ids  根据业务需求如果需要根据id查询则返回正常查询值
     * @return
     */
    public List<TypeDTO> getALlTypes(Integer ... ids){
        List<TypeDTO> allTag=null;
        if(ids.length==0){
            allTag = tagAndTypeExe.getAllTypes();
        }else{
            allTag=tagAndTypeExe.getTypeByIds(Arrays.asList(ids));
        }
        return allTag;
    }

}
