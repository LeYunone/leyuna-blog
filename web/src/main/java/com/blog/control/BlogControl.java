package com.blog.control;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.domain.BlogDomain;
import com.blog.api.domain.SearchDomain;
import com.blog.api.domain.UserDomain;
import com.blog.api.dto.BlogDTO;
import com.blog.api.dto.LuceneDTO;
import com.blog.api.dto.NoticeDTO;
import com.blog.api.dto.WebHistoryDTO;
import com.blog.bean.BlogBean;
import com.blog.bean.NoticeBean;
import com.blog.bean.ResponseBean;
import com.blog.error.SystemAsserts;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.TransformationUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author pengli
 * @create 2021-08-10 15:04
    博客控制器- 控制博客行为 网站公告行为 以及各种公示行为
 */
@RestController()
@RequestMapping("/blog")
public class BlogControl extends SysBaseControl {

    @Autowired
    private BlogDomain blogDomain;
    @Autowired
    private UserDomain userDomain;
    @Autowired
    private SearchDomain searchDomain;
    /**
     * 发布博客
     * @param blogBean
     * @return
     */
    @PostMapping("/addBlog")
    public ResponseBean addBlog(@RequestBody BlogBean blogBean){
        userDomain.checkLock();
        String[] tags = blogBean.getTags();
        if(tags.length!=0){
            StringBuilder stringBuilder=new StringBuilder();
            for(String tag:tags){
                stringBuilder.append(tag+",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            blogBean.setTag(stringBuilder.toString());
        }
        boolean b = blogDomain.addBlog(TransformationUtil.copyToDTO(blogBean, BlogDTO.class));
        if(b){
            return successResponseBean();
        }else{
            return failResponseBean(SystemAsserts.ADD_BLOG_FAIL.getMsg());
        }
    }

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogs")
    public ResponseBean getAllBlogs(@RequestParam(required = false) Integer index,
                                    @RequestParam(required = false) Integer size,
                                    @RequestParam(required = false) Integer typeId,
                                    @RequestParam(required = false) String  tags,
                                    @RequestParam(required = false) String conditionName){
        Page<BlogDTO> blogsByPage = blogDomain.getBlogsByPage(index, size, typeId, tags,conditionName);
        return successResponseBean(blogsByPage);
    }

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogIndex")
    public ResponseBean getAllBlogsByTypeOrTag(Integer index,Integer size,
                                            @RequestParam(required = false) Integer typeId,
                                            @RequestParam(required = false)String tagName){
        Page<BlogDTO> blogsByPage = blogDomain.getBlogsByPage(index, size, typeId, tagName,null);
        ResponseBean responseBean = successResponseBean(blogsByPage);
        //包装有多少条博客
        responseBean.setObjData(blogsByPage.getTotal());
        return responseBean;
    }

    @GetMapping("/blog/{id}")
    public ResponseBean getBlogById(@PathVariable(value = "id")Integer blogId){
        BlogDTO blogById = blogDomain.openBlogById(blogId);
        return successResponseBean(blogById);
    }

    @PostMapping("/edit")
    public ResponseBean editBlog(@RequestBody BlogBean blogBean){
        userDomain.checkLock();
        boolean b = blogDomain.updateBlog(TransformationUtil.copyToDTO(blogBean, BlogDTO.class));
        if(b){
            return successResponseBean();
        }else{
            return failResponseBean(SystemAsserts.UPDATE_BLOG_FAIL.getMsg());
        }
    }

    /**
     * 查询网站更新历史之类的
     * @param index
     * @param size
     * @return
     */
    @RequestMapping("/history")
    public ResponseBean getWebHistory(Integer index,Integer size){
        Page<NoticeDTO> webHistory=blogDomain.getNoticePage(index, size,null,0);
        return successResponseBean(webHistory);
    }
    /**
     *  发布网站更新历史
     * @return
     */
    @PostMapping("/addWebNotice")
    public ResponseBean addWebNotice(@RequestBody NoticeBean noticeBean){
        userDomain.checkLock();
        boolean b = blogDomain.addNotice(TransformationUtil.copyToDTO(noticeBean, NoticeDTO.class));
        if(b){
            return successResponseBean();
        }else{
            return failResponseBean(SystemAsserts.ADD_BLOG_FAIL.getMsg());
        }
    }

    /**
     * 站内搜索博客
     * @param key
     * @param index
     * @param size
     * @return
     */
    @GetMapping("/search")
    public ResponseBean blogSearch(String key,Integer index,Integer size){
        //查关键词
        LuceneDTO blogFromSearch = searchDomain.getBlogFromSearch(key, index, size);
        //
        if(null!=blogFromSearch){
            return successResponseBean(blogFromSearch);
        }else{
            return failResponseBean(SystemAsserts.QUERY_SEARCH.getMsg());
        }
    }

    /**
     * 创建当前数据库中所有博客的索引库
     * @return
     */
    @PostMapping("/createDocument")
    public ResponseBean createAllBlogDocument(){
        userDomain.checkLock();
        boolean blogSearch = searchDomain.createBlogSearch();
        if(blogSearch){
            return successResponseBean();
        }else{
            return failResponseBean();
        }
    }

    /**
     * 查询站内所有公告
     * @param index
     * @param size
     * @param conditionName
     * @return
     */
    @GetMapping("/notices")
    public ResponseBean getAllNotice(@RequestParam(required = false) Integer index,
                                    @RequestParam(required = false) Integer size,
                                    @RequestParam(required = false) String conditionName){
        Page<NoticeDTO> noticePage = blogDomain.getNoticePage(index, size, conditionName, 0);
        return successResponseBean(noticePage);
    }

    /**
     * 根据类型和id查询公告
     * @param id
     * @param type
     * @return
     */
    @GetMapping("/notice/{id}/{type}")
    public ResponseBean getNoticeOne(@PathVariable(value = "id")Integer id,@PathVariable(value = "type")Integer type){
        NoticeDTO noticeOne = blogDomain.getNoticeOne(id, type);
        return successResponseBean(noticeOne);
    }

    @PostMapping("/editNotice")
    public ResponseBean editNotice(@RequestBody NoticeBean noticeBean){
        userDomain.checkLock();
        boolean b = blogDomain.updateNotice(TransformationUtil.copyToDTO(noticeBean, NoticeDTO.class));
        if(b){
            return successResponseBean();
        }else{
            return failResponseBean(SystemAsserts.UPDATE_BLOG_FAIL.getMsg());
        }
    }
}
