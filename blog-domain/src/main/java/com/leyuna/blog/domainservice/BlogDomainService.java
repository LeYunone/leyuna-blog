package com.leyuna.blog.domainservice;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyuna.blog.co.blog.TagCO;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.domain.TagE;
import com.leyuna.blog.domain.TypeE;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengli
 * @date 2022-03-10
 * 博客领域业务-复杂 
 */
@Service
public class BlogDomainService {

    /**
     * 处理添加博客后的后续内容
     */
    public void addBlogAfter(BlogE blogDTO){
        String tag=blogDTO.getTag();
        //先处理新增标签
        if (StringUtils.isNotEmpty(tag)) {
            String[] split = tag.split(",");
            List<TagE> addList = new ArrayList<>();
            for (String name : split) {
                //判断有没有这个名字的标签，如果没有的话则需要创建这个标签.
                List<TagCO> tags = TagE.queryInstance().setTagName(name).selectByCon();
                //如果查不到则创建这个标签
                if (CollectionUtils.isEmpty(tags)) {
                    TagE build = TagE.queryInstance().setTagName(name).setcreateDt(LocalDateTime.now())
                            .setupdateDt(LocalDateTime.now()).setUseCount(1);
                    addList.add(build);
                } else {
                    //如果查到了，则说明这个标签使用次数需要添加一
                    TagCO tagCO = tags.get(0);
                    boolean update = TagE.queryInstance().setId(tagCO.getId()).setUseCount(tagCO.getUseCount() + 1).update();
                    AssertUtil.isTrue(update,SystemErrorEnum.UPDATE_TAG_FALE.getMsg());
                }
            }
            //如果addlist有值，则说明需要添加标签到数据库中
            if (CollectionUtils.isNotEmpty(addList)) {
                boolean b = TagE.batchCreate(addList);
                AssertUtil.isTrue(b,SystemErrorEnum.ADD_TAG_FALE.getMsg());
            }
        }

        //更新分类
        String type = blogDTO.getType();
        TypeCO typeCO = TypeE.queryInstance().setId(type).selectById();
        TypeE.queryInstance().setId(type).setUseCount(typeCO.getUseCount()+1).update();
    }
}
