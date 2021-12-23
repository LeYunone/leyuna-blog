package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.BlogBean;
import com.leyuna.blog.bean.NoticeBean;
import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.co.BlogCO;
import com.leyuna.blog.co.LuceneCO;
import com.leyuna.blog.co.WebHistoryCO;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.domain.WebHistoryE;
import com.leyuna.blog.error.SystemAsserts;
import com.leyuna.blog.service.BlogDomain;
import com.leyuna.blog.service.SearchDomain;
import com.leyuna.blog.service.UserDomain;
import com.leyuna.blog.util.TransformationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pengli
 * @create 2021-08-10 15:04
    博客控制器- 控制博客行为 网站公告行为 以及各种公示行为
 */
@RestController()
@RequestMapping("/blog")
public class BlogControl{

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
        boolean b = blogDomain.addBlog(TransformationUtil.copyToDTO(blogBean, BlogE.class));
        if(b){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(SystemAsserts.ADD_BLOG_FAIL.getMsg());
        }
    }

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogs")
    public ResponseBean getAllBlogs(@RequestParam(required = false,defaultValue = "1") Integer index,
                                    @RequestParam(required = false,defaultValue = "20") Integer size,
                                    @RequestParam(required = false) String typeId,
                                    @RequestParam(required = false) String  tags,
                                    @RequestParam(required = false) String conditionName){
        Page<BlogCO> blogsByPage = blogDomain.getBlogsByPage(index, size, typeId, tags,conditionName);
        return ResponseBean.of(blogsByPage);
    }

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogIndex")
    public ResponseBean getAllBlogsByTypeOrTag(Integer index,Integer size,
                                            @RequestParam(required = false) String typeId,
                                            @RequestParam(required = false)String tagName){
        Page<BlogCO> blogsByPage = blogDomain.getBlogsByPage(index, size, typeId, tagName,null);
        return ResponseBean.of(blogsByPage);
    }

    @GetMapping("/blog/{id}")
    public ResponseBean getBlogById(@PathVariable(value = "id")String blogId){
        BlogCO blogById = blogDomain.openBlogById(blogId);
        return ResponseBean.of(blogById);
    }

    @PostMapping("/edit")
    public ResponseBean editBlog(@RequestBody BlogBean blogBean){
        userDomain.checkLock();
        boolean b = blogDomain.updateBlog(TransformationUtil.copyToDTO(blogBean, BlogE.class));
        if(b){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(SystemAsserts.UPDATE_BLOG_FAIL.getMsg());
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
        Page<WebHistoryCO> webHistory=blogDomain.getNoticePage(index, size,null,0);
        return ResponseBean.of(webHistory);
    }
    /**
     *  发布网站更新历史
     * @return
     */
    @PostMapping("/addWebNotice")
    public ResponseBean addWebNotice(@RequestBody NoticeBean noticeBean){
        userDomain.checkLock();
        boolean b = blogDomain.addNotice(TransformationUtil.copyToDTO(noticeBean, WebHistoryE.class));
        if(b){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(SystemAsserts.ADD_BLOG_FAIL.getMsg());
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
        LuceneCO blogFromSearch = searchDomain.getBlogFromSearch(key, index, size);
        //
        if(null!=blogFromSearch){
            return ResponseBean.of(blogFromSearch);
        }else{
            return ResponseBean.buildFailure(SystemAsserts.QUERY_SEARCH.getMsg());
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
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure();
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
        Page<WebHistoryCO> noticePage = blogDomain.getNoticePage(index, size, conditionName, 0);
        return ResponseBean.of(noticePage);
    }

    /**
     * 根据类型和id查询公告
     * @param id
     * @param type
     * @return
     */
    @GetMapping("/notice/{id}/{type}")
    public ResponseBean getNoticeOne(@PathVariable(value = "id")String id,@PathVariable(value = "type")Integer type){
        WebHistoryCO noticeOne = blogDomain.getNoticeOne(id, type);
        return ResponseBean.of(noticeOne);
    }

    @PostMapping("/editNotice")
    public ResponseBean editNotice(@RequestBody NoticeBean noticeBean){
        userDomain.checkLock();
        boolean b = blogDomain.updateNotice(TransformationUtil.copyToDTO(noticeBean, WebHistoryE.class));
        if(b){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(SystemAsserts.UPDATE_BLOG_FAIL.getMsg());
        }
    }
}
