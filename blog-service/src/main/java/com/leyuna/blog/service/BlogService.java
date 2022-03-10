package com.leyuna.blog.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.co.blog.WebHistoryCO;
import com.leyuna.blog.command.*;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.domain.TagE;
import com.leyuna.blog.domain.WebHistoryE;
import com.leyuna.blog.error.SystemAsserts;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.TransformationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
public class BlogService {

    @Autowired
    private BlogExe blogExe;
    @Autowired
    private NoticeExe noticeExe;
    @Autowired
    private TagAndTypeExe tagAndTypeExe;
    @Autowired
    private CacheExe clearCacheExe;
    @Autowired
    private LuceneExe luceneExe;
    /**
     * 分页取得博客   根据分类或者标签或者查所有
     * @param index
     * @param size
     * @param type
     * @param tags
     * @return
     */
    public DataResponse getBlogsByPage(Integer index, Integer size, String type, String tags, String conditionName){
        Page<BlogCO> result=null;
        //查询所有
        if(type==null && StringUtils.isEmpty(tags)){
            result = blogExe.getAllBlogByPage(index, size,conditionName);
        }else{
            result = blogExe.getBlogByPage(index, size, type, tags,conditionName);
        }
        return DataResponse.of(result);
    }
    /**
     * 发布博客
     * @return
     */
    @Transactional
    public boolean addBlog(BlogE blogDTO){
        String tag = blogDTO.getTag();
        blogDTO.setClickCount(0);
        blogDTO.setCommentCount(0);
        blogDTO.setCreateTime(LocalDateTime.now());
        blogDTO.setUpdateTime(LocalDateTime.now());
        String blogId=blogExe.addBlog(blogDTO);
        boolean b=true;
        if(null==blogId){
            b=false;
        }
        if(b){
            //先处理新增标签
            if(StringUtils.isNotEmpty(tag)){
                String[] split = tag.split(",");
                List<TagE> addList=new ArrayList<>();
                for(String name:split){
                    //判断有没有这个名字的标签，如果没有的话则需要创建这个标签.
                    boolean tagByName = tagAndTypeExe.getTagByName(name);
                    //如果查不到则创建这个标签
                    if(!tagByName){
                        TagE build = TagE.queryInstance().setTagName(name).setCreateTime(LocalDateTime.now())
                                .setLastUserTime(LocalDateTime.now()).setUseCount(1);
                        addList.add(build);
                    }else{
                        //如果查到了，则说明这个标签使用次数需要添加一
                        boolean is = tagAndTypeExe.addTagUseCountByName(name);
                        if(!is){
                            AssertUtil.isTrue(SystemAsserts.UPDATE_TAG_FALE.getMsg()) ;
                        }
                    }
                }
                //如果addlist有值，则说明需要添加标签到数据库中
                if(CollectionUtils.isNotEmpty(addList)){
                    boolean is = tagAndTypeExe.addTags(addList);
                    if(!is){
                        AssertUtil.isTrue(SystemAsserts.ADD_TAG_FALE.getMsg());
                    }
                }
            }


            //更新分类 和标签的最后使用时间
            String type = blogDTO.getType();
            tagAndTypeExe.addTypeUseCount(type);
            tagAndTypeExe.updateTagsAndTypes(tag,type);

            //添加改博客的索引库文档
            List<BlogE> list=new ArrayList<>();
            blogDTO.setId(blogId);
            list.add(blogDTO);
            try {
                luceneExe.addBlogDir(list);
            } catch (IOException e) {
                AssertUtil.isTrue(SystemAsserts.CREATE_DOCUMENT_FALE.getMsg());
            }

            //更新博客\标签\分类\分类缓存
            clearCacheExe.clearBlogQueryCache();
            clearCacheExe.clearTagQueryCache();
            clearCacheExe.clearTypeQueryCache();
            clearCacheExe.clearTypeNavQueryCache();
            return b;
        }
        AssertUtil.isTrue(SystemAsserts.ADD_BLOG_FAIL.getMsg());
        return false;
    }

    /**
     * 打开文章
     * @param id
     * @return
     */
    @Transactional
    public BlogCO openBlogById(String id){
        //找到博客
        BlogCO blogById = blogExe.getBlogById(id);
        //博客增加一点击量
        boolean b = blogExe.addBlogClickCount(id, blogById.getClickCount() + 1);
        if(!b){
            //回滚事务
            throw new RuntimeException();
        }
        return blogById;
    }

    /**
     * 更新文章
     * @param blogDTO
     * @return
     */
    public boolean updateBlog(BlogE blogDTO){
        //设置最新更新时间
        blogDTO.setUpdateTime(LocalDateTime.now());
        boolean update = blogDTO.update();
        if(update){
            //更新索引库中的Key
            try {
                luceneExe.updateBlogDocument(blogDTO);
            } catch (IOException e) {
                return false;
            }
            //清除这篇文章的缓存
            clearCacheExe.clearBlogQueryByIdCache(blogDTO.getId());
            clearCacheExe.clearBlogQueryCache();
        }
        return update;
    }

    /**
     * 添加各类版本的公示 方法
     * @param noticeDTO
     * @return
     */
    public boolean addNotice(WebHistoryE noticeDTO){
        Integer type = noticeDTO.getType();
        switch (type){
            case 0:
                //0 网站更新公告
                boolean b = noticeExe.addHistory(TransformationUtil.copyToDTO(noticeDTO, WebHistoryE.class));
                if(b){
                    clearCacheExe.clearWebHistoryCache();
                }
                return b;
            default:
                return false;
        }
    }

    /**
     * 分页获取公告
     * @param index
     * @param size
     * @param conditionName
     * @param type
     * @return
     */
    public Page<WebHistoryCO> getNoticePage(Integer index, Integer size, String conditionName, int type){
        switch (type){
            case 0:
                Page<WebHistoryCO> webHistory=null;
                //查询网站更新公告
                if(StringUtils.isNotEmpty(conditionName)){
                   webHistory = noticeExe.getWebHistory(index,size,conditionName);
                }else{
                   webHistory = noticeExe.getWebHistory(index, size);
                }
               return webHistory;
            default:
                return null;
        }
    }

    /**
     * 得到一条公告
     * @param id
     * @param type
     * @return
     */
    public WebHistoryCO getNoticeOne(String id,int type){
        switch (type){
            case 0:
                WebHistoryCO webHistoryById = noticeExe.getWebHistoryById(id);
                return webHistoryById;
            default:
                return null;
        }
    }

    /**
     * 更新公告
     * @param noticeDTO
     * @return
     */
    public boolean updateNotice(WebHistoryE noticeDTO){
        switch (noticeDTO.getType()){
            case 0:
                boolean b = noticeExe.updateWebHis(noticeDTO);
                if(b){
                    clearCacheExe.clearWebHistoryCache();
                }
                return b;
            default:
                return false;
        }
    }
}
