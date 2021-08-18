package com.blog.control;

import com.blog.api.domain.TagTypeDomain;
import com.blog.api.dto.ResultDTO;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import com.blog.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
     * @param ids
     * @return
     */
    @RequestMapping("/tags")
    public ResponseBean getTags(@RequestParam(required = false) Integer...ids){
        List<TagDTO> aLlTags = tagTypeDomain.getALlTags(ids);
        ResponseBean responseBean = successResponseBean(aLlTags);
        return  packPage(responseBean);
    }

    /**
     * 取一级分类
     * @param ids
     * @return
     */
    @RequestMapping("/types")
    public ResponseBean getTypes(@RequestParam(required = false) Integer...ids){
        List<TypeDTO> aLlTags = tagTypeDomain.getALlTypes(ids);
        ResponseBean responseBean = successResponseBean(aLlTags);
        return packPage(responseBean);
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
    @RequestMapping("/updateTagsAndTypes")
    public ResponseBean updateTagsAndTypes(List<TagDTO> tags,List<TypeDTO> types){
        ResultDTO resultDTO = tagTypeDomain.updateTypesOrTags(tags, types);
        if(resultDTO.getMessages()==null){
            return successResponseBean();
        }else{
            return failResponseBean(resultDTO.getMessages());
        }
    }

}
