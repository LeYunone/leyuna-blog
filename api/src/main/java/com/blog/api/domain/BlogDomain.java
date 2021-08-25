package com.blog.api.domain;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.command.BlogExe;
import com.blog.api.command.TagAndTypeExe;
import com.blog.api.dto.BlogDTO;
import com.blog.api.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.StringUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengli
 * @create 2021-08-16 13:29
 *
 * 博客领域
 */
@Service
public class BlogDomain {

    @Autowired
    private BlogExe blogExe;
    @Autowired
    private TagAndTypeExe tagAndTypeExe;
    /**
     * 分页取得博客   根据分类或者标签或者查所有
     * @param index
     * @param size
     * @param type
     * @param tags
     * @return
     */
    public Page<BlogDTO> getBlogsByPage(Integer index,Integer size,Integer type,String tags,String conditionName){
        Page<BlogDTO> result=null;
        //查询所有
        if(type==null && StringUtils.isEmpty(tags)){
            result = blogExe.getAllBlogByPage(index, size,conditionName);
        }else{
            result = blogExe.getBlogByPage(index, size, type, tags,conditionName);
        }
        return result;
    }
    public int getBlogsByTypeCount(Integer type){
        int blogByTypeCount = blogExe.getBlogByTypeCount(type);
        return blogByTypeCount;
    }
    /**
     * 发布博客
     * @return
     */
    @Transactional
    public boolean addBlog(BlogDTO blogDTO){
        String tag = blogDTO.getTag();
        //先处理新增标签
        if(StringUtils.isNotEmpty(tag)){
            String[] split = tag.split(",");
            List<TagDTO> addList=new ArrayList<>();
            for(String name:split){
                //判断有没有这个名字的标签，如果没有的话则需要创建这个标签.
                boolean tagByName = tagAndTypeExe.getTagByName(name);
                //如果查不到则创建这个标签
                if(!tagByName){
                    TagDTO build = TagDTO.builder().tagName(name).createTime(LocalDateTime.now())
                            .lastUserTime(LocalDateTime.now()).useCount(0).build();
                    addList.add(build);
                }
            }
            //如果addlist有值，则说明需要添加标签到数据库中
            if(CollectionUtils.isNotEmpty(addList)){
                boolean b = tagAndTypeExe.addTags(addList);
                if(!b){
                    throw new RuntimeException();
                }
            }
        }

        blogDTO.setClickCount(0);
        blogDTO.setCommentCount(0);
        blogDTO.setCreateTime(LocalDateTime.now());
        blogDTO.setUpdateTime(LocalDateTime.now());
        boolean b = blogExe.addBlog(blogDTO);
        if(!b){
            throw new RuntimeException();
        }else{
            //更新分类 和标签的最后使用次数
            String tagUpdates = blogDTO.getTag();
            Integer type = blogDTO.getType();
            tagAndTypeExe.updateTagsAndTypes(tagUpdates,type);
        }
        return b;
    }

    /**
     * 打开文章
     * @param id
     * @return
     */
    @Transactional
    public BlogDTO openBlogById(Integer id){
        //找到博客
        BlogDTO blogById = blogExe.getBlogById(id);

        //博客增加一点击量
        boolean b = blogExe.addBlogClickCount(id, blogById.getClickCount() + 1);
        if(!b){
            //回滚事务
            throw new RuntimeException();
        }
        return blogById;
    }

    public boolean updateBlog(BlogDTO blogDTO){
        blogDTO.setUpdateTime(LocalDateTime.now());
        boolean b = blogExe.updateBlog(blogDTO);
        return b;
    }
}
