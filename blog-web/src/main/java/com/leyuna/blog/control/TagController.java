package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.model.co.TagCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.model.dto.TagDTO;
import com.leyuna.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-12 10:17
 *
 * 标签以及分类 操作控制器
 */
@RestController()
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 取标签  【二级分类】
     * @param
     * @return
     */
    @RequestMapping("/tags")
    public DataResponse<Page<TagCO>> getTags(TagDTO query){
        Page<TagCO> aLlTags = tagService.getALlTags(query);
        return DataResponse.of(aLlTags);
    }

    /**
     * 添加标签  只要添加名字就好了
     * @param tags
     * @return
     */
    @RequestMapping("/add")
    public DataResponse addTags(@RequestParam(required = false) List<String> tags){
        return tagService.addTags(tags);
    }

    /**
     * 删除标签
     * @param tags
     * @return
     */
    @PostMapping("/delete")
    public DataResponse deleteTags(@RequestParam(required = false) List<String> tags){
        return tagService.deleteTags(tags);
    }

    /**
     * 更新标签和分类
     * @return
     */
    @PostMapping("/update")
    public DataResponse updateTags(@RequestBody TagDTO tagDTO){
        return tagService.updateTag(tagDTO);
    }
}
