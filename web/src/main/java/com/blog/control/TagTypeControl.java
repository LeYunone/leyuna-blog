package com.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.domain.TagTypeDomain;
import com.blog.api.dto.ResultDTO;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import com.blog.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import util.CollectionUtil;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 10:17
 *
 * 标签以及分类 操作控制器
 */
@RestController()
@RequestMapping("/tagType")
public class TagTypeControl extends SysBaseControl{

    @Autowired
    private TagTypeDomain tagTypeDomain;

    /**
     * 取标签  【二级分类】
     * @param
     * @return
     */
    @RequestMapping("/tags")
    public ResponseBean getTags(@RequestParam(required = false)Integer pageIndex,
                                @RequestParam(required = false)Integer pageSize,
                                @RequestParam(required = false)String conditionName){
        Page<TagDTO> aLlTags = tagTypeDomain.getALlTags(pageIndex,pageSize,conditionName);
        ResponseBean responseBean = successResponseBean(aLlTags.getRecords());
        responseBean.setPage(aLlTags);
        return  responseBean;
    }

    @GetMapping("/tagsId")
    public ResponseBean getTagsById(Integer...ids){
        List<TagDTO> tagsByIds = tagTypeDomain.getTagsByIds(ids);
        return successResponseBean(tagsByIds);
    }

    /**
     * 取一级分类
     * @param
     * @return
     */
    @RequestMapping("/types")
    public ResponseBean getTypes(@RequestParam(required = false)Integer pageIndex,
                                 @RequestParam(required = false)Integer pageSize,
                                 @RequestParam(required = false)String conditionName){
        Page<TypeDTO> aLlTags = tagTypeDomain.getALlTypes(pageIndex,pageSize,conditionName);
        ResponseBean responseBean = successResponseBean(aLlTags.getRecords());
        responseBean.setPage(aLlTags);
        return  responseBean;
    }

    @GetMapping("/typesId")
    public ResponseBean getTypesById(Integer...ids){
        List<TypeDTO> tagsByIds = tagTypeDomain.getTypesByIds(ids);
        return successResponseBean(tagsByIds);
    }

    /**
     * 添加分类或标签
     * @param tags
     * @param types
     * @return
     */
    @RequestMapping("/addTagsAndTypes")
    public ResponseBean addTagsAndTypes(List<TagDTO> tags,List<TypeDTO> types){
        ResultDTO resultDTO = tagTypeDomain.addTypesOrTags(tags, types);
        if(resultDTO.getMessages()==null){
            return successResponseBean();
        }else{
            return failResponseBean(resultDTO.getMessages());
        }
    }

    /**
     * 删除分类或标签
     * @param tags
     * @param types
     * @return
     */
    @RequestMapping("/deleteTagsAndTypes")
    public ResponseBean deleteTagsAndTypes(List<Integer> tags,List<Integer> types){
        ResultDTO resultDTO = tagTypeDomain.deleteTypesOrTags(tags, types);
        if(resultDTO.getMessages()==null){
            return successResponseBean();
        }else{
            return failResponseBean(resultDTO.getMessages());
        }
    }

    /**
     * 更新分类或标签
     * @param tags
     * @param types
     * @return
     */
    @GetMapping("/updateTagsAndTypes")
    public ResponseBean updateTagsAndTypes(@RequestBody(required = false) TagDTO tags,
                                           @RequestBody(required = false) TypeDTO types){
        ResultDTO resultDTO = tagTypeDomain.updateTypesOrTags(tags, types);
        if(resultDTO.getMessages()==null){
            return successResponseBean();
        }else{
            return failResponseBean(resultDTO.getMessages());
        }
    }

}
