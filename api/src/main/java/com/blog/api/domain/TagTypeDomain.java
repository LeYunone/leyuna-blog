package com.blog.api.domain;

import com.blog.api.Error.ErrorMessage;
import com.blog.api.command.TagAndTypeExe;
import com.blog.api.dto.ResultDTO;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    /**
     * 添加一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    public ResultDTO addTypesOrTags(List<TagDTO> tags,List<TypeDTO> types){
        ResultDTO resultDTO=new ResultDTO();
        if(!CollectionUtils.isEmpty(types)){
            boolean b = tagAndTypeExe.addTypes(types);
            if(!b){
                resultDTO.addMessage(ErrorMessage.ADD_TYPE_FALE);
            }
        }
        if(!CollectionUtils.isEmpty(tags)){
            boolean b = tagAndTypeExe.addTags(tags);
            if(!b){
                resultDTO.addMessage(ErrorMessage.ADD_TAG_FALE);
            }
        }
        return resultDTO;
    }

    /**
     * 删除一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    @Transactional
    public ResultDTO deleteTypesOrTags(List<Integer> tags,List<Integer> types){
        ResultDTO resultDTO=new ResultDTO();
        try {
            if(!CollectionUtils.isEmpty(types)){
                tagAndTypeExe.deleteTypes(types);
            }
        }catch (Exception e){
            resultDTO.addMessage(ErrorMessage.DELETE_TYPE_FALE);
        }
        try {
            if(!CollectionUtils.isEmpty(tags)){
                tagAndTypeExe.deleteTags(tags);
            }
        }catch (Exception e) {
            resultDTO.addMessage(ErrorMessage.DELETE_TAG_FALE);
        }
        return resultDTO;
    }


    /**
     * 更新一级分类【分类】  或者二级分类【标签】
     * @param tags
     * @param types
     * @return
     */
    @Transactional
    public ResultDTO updateTypesOrTags(List<TagDTO> tags,List<TypeDTO> types){
        ResultDTO resultDTO=new ResultDTO();
        if(!CollectionUtils.isEmpty(types)){
            boolean b = tagAndTypeExe.updateTypes(types);
            if(!b){
                resultDTO.addMessage(ErrorMessage.UPDATE_TYPE_FALE);
            }
        }
        if(!CollectionUtils.isEmpty(tags)){
            boolean b = tagAndTypeExe.updateTags(tags);
            if(!b){
                resultDTO.addMessage(ErrorMessage.UPDATE_TAG_FALE);
            }
        }
        return resultDTO;
    }
}
