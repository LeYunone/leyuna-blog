package com.blog.control;

import com.blog.api.domain.TagTypeDomain;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import com.blog.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return successResponseBean(aLlTags);
    }

    @RequestMapping("/types")
    public ResponseBean getTypes(@RequestParam(required = false) Integer...ids){
        List<TypeDTO> aLlTags = tagTypeDomain.getALlTypes(ids);
        return successResponseBean(aLlTags);
    }
}
