package com.blog.api.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.Error.ErrorMessage;
import com.blog.api.command.TagAndTypeExe;
import com.blog.api.dto.ResultDTO;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import com.blog.daoservice.entry.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
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
     * 根据id查询标签
     * @param ids
     * @return
     */
    public List<TagDTO> getTagsByIds(Integer ... ids){
        List<TagDTO> allTag=null;
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
    public List<TypeDTO> getTypesByIds(Integer ... ids){
        List<TypeDTO> allType=null;
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
    public Page<TagDTO> getALlTags(Integer pageIndex,Integer pageSize,String conditionName){
        Page<TagDTO> iPage = tagAndTypeExe.getAllTags(pageIndex,pageSize,conditionName);
        iPage.getRecords().stream().forEach(tag->{
            LocalDateTime lastTime=tag.getLastUserTime();
            //如果最后使用的时间加了一个月还在现在的时间前面，那么就说明这个标签很久没用了
            if(LocalDateTime.now().isBefore(lastTime.plusMonths(1))){
                tag.setUserStatus("hot");
            }else{
                tag.setUserStatus("cold");
            }
        });
        return iPage;
    }

    /**
     * 查询所有的分类  分页 加上 模糊查询
     * @param
     * @return
     */
    public Page<TypeDTO> getALlTypes(Integer pageIndex,Integer pageSize,String conditionName){
        Page<TypeDTO> iPage = tagAndTypeExe.getAllTypes(pageIndex,pageSize,conditionName);
        iPage.getRecords().stream().forEach(tag->{
            LocalDateTime lastTime=tag.getLastUserTime();
            //如果最后使用的时间加了一个月还在现在的时间前面，那么就说明这个标签很久没用了
            if(LocalDateTime.now().isBefore(lastTime.plusMonths(1))){
                tag.setUserStatus("hot");
            }else{
                tag.setUserStatus("cold");
            }
        });
        return iPage;
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
