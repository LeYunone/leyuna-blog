package com.blog.api.domain;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.Error.ErrorMessage;
import com.blog.api.command.BlogExe;
import com.blog.api.command.ClearCacheExe;
import com.blog.api.command.TagAndTypeExe;
import com.blog.api.dto.BlogDTO;
import com.blog.api.dto.NoticeDTO;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.WebHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.AssertUtil;
import util.StringUtil;
import util.TransformationUtil;

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
    @Autowired
    private ClearCacheExe clearCacheExe;
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
        clearCacheExe.clearBlogQueryCache();
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
                            .lastUserTime(LocalDateTime.now()).useCount(1).build();
                    addList.add(build);
                }else{
                    //如果查到了，则说明这个标签使用次数需要添加一
                    boolean b = tagAndTypeExe.addTagUseCountByName(name);
                    if(!b){
                        AssertUtil.isTrue(ErrorMessage.UPDATE_TAG_FALE) ;
                    }
                }
            }
            //如果addlist有值，则说明需要添加标签到数据库中
            if(CollectionUtils.isNotEmpty(addList)){
                boolean b = tagAndTypeExe.addTags(addList);
                if(!b){
                    AssertUtil.isTrue(ErrorMessage.ADD_TAG_FALE);
                }
            }
        }

        blogDTO.setClickCount(0);
        blogDTO.setCommentCount(0);
        blogDTO.setCreateTime(LocalDateTime.now());
        blogDTO.setUpdateTime(LocalDateTime.now());
        boolean b = blogExe.addBlog(blogDTO);
        if(!b){
            AssertUtil.isTrue(ErrorMessage.ADD_BLOG_FALE);
        }else{
            //更新分类 和标签的最后使用时间
            String tagUpdates = blogDTO.getTag();
            Integer type = blogDTO.getType();
            tagAndTypeExe.updateTagsAndTypes(tagUpdates,type);

            //更新博客缓存
            clearCacheExe.clearBlogQueryCache();
            //更新标签缓存
            clearCacheExe.clearTagQueryCache();
            //更新分类缓存
            clearCacheExe.clearTypeQueryCache();
            //更新分类导航缓存
            clearCacheExe.clearTypeNavQueryCache();
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
        clearCacheExe.clearBlogQueryByIdCache(id);
        return blogById;
    }

    public boolean updateBlog(BlogDTO blogDTO){
        //设置最新更新时间
        blogDTO.setUpdateTime(LocalDateTime.now());
        boolean b = blogExe.updateBlog(blogDTO);
        if(b){
            clearCacheExe.clearBlogQueryByIdCache(blogDTO.getId());
        }
        return b;
    }

    public Page<WebHistoryDTO> getWebHistory(Integer index,Integer size){
        Page<WebHistoryDTO> webHistory = blogExe.getWebHistory(index, size);
        return  webHistory;
    }

    /**
     * 添加各类版本的公示 方法
     * @param noticeDTO
     * @return
     */
    public boolean addNotice(NoticeDTO noticeDTO){
        Integer type = noticeDTO.getType();
        switch (type){
            case 0:
                //0 网站更新公告
                boolean b = blogExe.addHistory(TransformationUtil.copyToDTO(noticeDTO, WebHistoryDTO.class));
                if(b){
                    clearCacheExe.clearWebHistoryCache();
                }
                return b;
            default:
                return false;
        }
    }
}
