package com.leyuna.blog.control;

import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.NoticeBean;
import com.leyuna.blog.co.blog.LuceneCO;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.error.SystemErrorEnum;
import com.leyuna.blog.service.BlogService;
import com.leyuna.blog.service.SearchService;
import com.leyuna.blog.service.UserService;
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
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Autowired
    private SearchService searchService;
    /**
     * 发布博客
     * @param blogBean
     * @return
     */
    @PostMapping("/addBlog")
    public DataResponse addBlog(@RequestBody BlogBean blogBean){
        userService.checkLock();
        String[] tags = blogBean.getTags();
        if(tags.length!=0){
            StringBuilder stringBuilder=new StringBuilder();
            for(String tag:tags){
                stringBuilder.append(tag+",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            blogBean.setTag(stringBuilder.toString());
        }
        boolean b = blogService.addBlog(TransformationUtil.copyToDTO(blogBean, BlogE.class));
        if(b){
            return DataResponse.buildSuccess();
        }else{
            return DataResponse.buildFailure(SystemErrorEnum.ADD_BLOG_FAIL.getMsg());
        }
    }

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogs")
    public DataResponse getAllBlogs(@RequestParam(required = false) Integer index,
                                    @RequestParam(required = false) Integer size,
                                    @RequestParam(required = false) String typeId,
                                    @RequestParam(required = false) String  tags,
                                    @RequestParam(required = false) String conditionName){
        return blogService.getBlogsByPage(index, size, typeId, tags,conditionName);
    }

    /**
     * 十条十条取当前所有的博客  每触发一次前端分页自动翻一页
     * @return
     */
    @GetMapping("/blogIndex")
    public DataResponse getAllBlogsByTypeOrTag(Integer index,Integer size,
                                            @RequestParam(required = false) String typeId,
                                            @RequestParam(required = false)String tagName){
        return blogService.getBlogsByPage(index, size, typeId, tagName,null);
    }

    @GetMapping("/blog/{id}")
    public DataResponse getBlogById(@PathVariable(value = "id")String blogId){
        return blogService.openBlogById(blogId);
    }

    @PostMapping("/edit")
    public DataResponse editBlog(@RequestBody BlogBean blogBean){
        userService.checkLock();
        return blogService.updateBlog(blogBean);
    }

    /**
     * 查询网站更新历史之类的
     * @param index
     * @param size
     * @return
     */
    @RequestMapping("/history")
    public DataResponse getWebHistory(Integer index,Integer size){
        return blogService.getNoticePage(index, size,null,0);
    }
    /**
     *  发布网站更新历史
     * @return
     */
    @PostMapping("/addWebNotice")
    public DataResponse addWebNotice(@RequestBody NoticeBean noticeBean){
        userService.checkLock();
        return blogService.addNotice(noticeBean);
    }

    /**
     * 站内搜索博客
     * @param key
     * @param index
     * @param size
     * @return
     */
    @GetMapping("/search")
    public DataResponse blogSearch(String key,Integer index,Integer size){
        //查关键词
        LuceneCO blogFromSearch = searchService.getBlogFromSearch(key, index, size);
        //
        if(null!=blogFromSearch){
            return DataResponse.of(blogFromSearch);
        }else{
            return DataResponse.buildFailure(SystemErrorEnum.QUERY_SEARCH.getMsg());
        }
    }

    /**
     * 创建当前数据库中所有博客的索引库
     * @return
     */
    @PostMapping("/createDocument")
    public DataResponse createAllBlogDocument(){
        userService.checkLock();
        boolean blogSearch = searchService.createBlogSearch();
        if(blogSearch){
            return DataResponse.buildSuccess();
        }else{
            return DataResponse.buildFailure();
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
    public DataResponse getAllNotice(@RequestParam(required = false) Integer index,
                                    @RequestParam(required = false) Integer size,
                                    @RequestParam(required = false) String conditionName){
        return blogService.getNoticePage(index, size, conditionName, 0);
    }

    /**
     * 根据类型和id查询公告
     * @param id
     * @param type
     * @return
     */
    @GetMapping("/notice/{id}/{type}")
    public DataResponse getNoticeOne(@PathVariable(value = "id")String id,@PathVariable(value = "type")Integer type){
        return blogService.getNoticeOne(id, type);
    }

    @PostMapping("/editNotice")
    public DataResponse editNotice(@RequestBody NoticeBean noticeBean){
        userService.checkLock();
        return blogService.updateNotice(noticeBean);
    }
}
