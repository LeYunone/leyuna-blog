package com.leyuna.blog.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.constant.enums.MenuPositionEnum;
import com.leyuna.blog.constant.enums.MenuUrlEnum;
import com.leyuna.blog.dao.BlogDao;
import com.leyuna.blog.dao.MenuDao;
import com.leyuna.blog.dao.repository.entry.BlogDO;
import com.leyuna.blog.dao.repository.entry.MenuDO;
import com.leyuna.blog.model.co.BlogCO;
import com.leyuna.blog.model.dto.BlogDTO;
import com.leyuna.blog.model.query.BlogQuery;
import com.leyuna.blog.model.query.MenuQuery;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.TransformationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author pengli
 * @create 2021-08-16 13:29
 * <p>
 * 博客领域
 */
@Service
public class BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private MenuDao menuDao;


    /**
     * 分页取得博客
     *
     * @return
     */
    public Page<BlogCO> getBlogsByPage(BlogDTO blogDTO) {
        Page<BlogCO> blogCOPage = blogDao.queryBlog(blogDTO);
        return blogCOPage;
    }

    /**
     * 发布博客
     *
     * @return
     */
    @Transactional
//    @CacheEvict(cacheNames = {"blog:blogs", "blog:type", "blog:tag"}, allEntries = true)
    public void saveBlog(BlogDTO blog) {
        //发布文章主题内容
        BlogDO blogDO = new BlogDO();
        BeanUtil.copyProperties(blog,blogDO);
        boolean addBlog = blogDao.insertOrUpdate(blogDO);
        AssertUtil.isTrue(addBlog);
        String blogId = blog.getId();
        MenuDO menuDO = new MenuDO();
        if(StringUtils.isEmpty(blogId)){
            //新增
            menuDO.setMenuName(blog.getTitle());
            menuDO.setMenuPosition(MenuPositionEnum.ARTICLE_MENU.getCode());
            menuDO.setMenuUrl(MenuUrlEnum.ARTICLE_URL.getUrl(blogDO.getId()));
            menuDO.setMenuParentId(blog.getMenuId());
        }else{
            //更新
            MenuQuery menuQuery = new MenuQuery();
            menuQuery.setMenuPosition(MenuPositionEnum.ARTICLE_MENU.getCode());
            menuQuery.setMenuUrl(MenuUrlEnum.ARTICLE_URL.getUrl(blogDO.getId()));
            menuDO = menuDao.selectOne(menuQuery);
            AssertUtil.isFalse(ObjectUtil.isNull(menuDO),"操作失败：原菜单不存在");
            menuDO.setMenuParentId(blog.getMenuId());
        }
        boolean newMenu = menuDao.insertOrUpdate(menuDO);
        AssertUtil.isTrue(newMenu);
        //创建该文章的菜单索引
//        MenuDO menuDO = new MenuDO();
//        menuDO.setMenuName(blog.getTitle());
//        Integer blogType = blog.getBlogType();
//        menuDO.setMenuParentId(blogType);
//        boolean addMenu = menuDao.insertOrUpdate(menuDO);
//        AssertUtil.isTrue(addBlog || addMenu);

    }

    /**
     * 打开文章
     *
     * @param id
     * @return
     */
    public BlogCO getBlogById(String id) {
        BlogCO blogCO = blogDao.selectById(id, BlogCO.class);
        return blogCO;
    }

    /**
     * 更新文章
     *
     * @return
     */
    public void updateBlog(BlogDTO blog) {
        Integer blogType = blog.getBlogType();
        blogDao.insertOrUpdate(blogType);
    }

    /**
     * 获取刷题日记  -  leetcode题  随机  爬虫
     *
     * @return
     */
    public List<BlogCO> getLeetCode() {
        //随机获取刷题日记
        List<BlogCO> randomLeetCodeLog = blogDao.selectRandomList();
        return randomLeetCodeLog;
    }

    public IPage<BlogCO> getTopMenuBlogs(BlogQuery query) {
        Integer menuId = query.getMenuId();
        AssertUtil.isFalse(ObjectUtil.isNull(menuId), "menuId is not empty");
        //20XX转换为日期
        if (StrUtil.isNotBlank(query.getCreateDt())) {
            LocalDateTime parse = LocalDateTimeUtil.parse(query.getCreateDt() + "-01-01", DatePattern.NORM_DATE_PATTERN);
            query.setCreateDate(parse);
        }
        //获得menuId下所有博客的上级菜单
        MenuDO currentMenu = menuDao.selectById(menuId);
        Stack<MenuDO> stack = new Stack<>();
        stack.add(currentMenu);
        MenuQuery menuQuery = new MenuQuery();
        List<Integer> menuTopIds = new ArrayList<>();
        while (!stack.isEmpty()) {
            MenuDO pop = stack.pop();
            Integer currentMenuId = pop.getMenuId();
            menuQuery.setMenuParentId(currentMenuId);
            menuQuery.setMenuPosition(MenuPositionEnum.NAV_MENU.getCode());
            List<MenuDO> menuDOS = menuDao.selectByCon(menuQuery);
            if (CollectionUtil.isEmpty(menuDOS)) {
                menuTopIds.add(pop.getMenuId());
            }else{
                stack.addAll(menuDOS);
            }
        }
        query.setMenuTopIds(menuTopIds);
        IPage<BlogDO> blogDOIPage = blogDao.selectByMenuTopOrderTime(query);
        Page<BlogCO> blogCOPage = TransformationUtil.copyToPage(blogDOIPage, BlogCO.class);
        return blogCOPage;
    }

    public void exportLeetCode(){
        List<BlogDO> blogDOS = blogDao.selectLeetCode();
        Map<Integer,String> linkedHashMap = new HashMap<>();

        for(BlogDO blogDO : blogDOS){
            String title = blogDO.getTitle();
            String temp = "=#---\n" +
                    "title: "+blogDO.getTitle()+"\n" +
                    "category: 刷题日记\n" +
                    "tag:\n" +
                    "  - LeetCode\n" +
                    "head:\n" +
                    "  - - meta\n" +
                    "    - name: keywords\n" +
                    "      content: JVM,JDK,JRE,字节码详解,Java 基本数据类型,装箱和拆箱\n" +
                    "  - - meta\n" +
                    "    - name: description\n" +
                    "      content: 全网质量最高的Java基础常见知识点和面试题总结，希望对你有帮助！\n" +
                    "=#---\n";
            String blogContent = blogDO.getBlogContent();
            title = title.substring(0,title.lastIndexOf("."));
            System.out.println("\""+title+"\",");
            FileWriter writer = new FileWriter("D:/桌面文件/文章/刷题日记/"+title+".md");
            writer.write(temp+blogContent);

            String tempTitle = title.substring(title.lastIndexOf("-")+1);
            linkedHashMap.put(Integer.valueOf(tempTitle),title);
        }
        Set<Integer> strings = linkedHashMap.keySet();
        ArrayList<Integer> integers = CollectionUtil.newArrayList(strings);
        Integer[] integers1 = integers.toArray(new Integer[]{});
        Arrays.sort(integers1);
        for(Integer  key : integers1){
            System.out.println(linkedHashMap.get(key));
        }
    }

    public void exportNormalNotes(){
        List<BlogDO> blogDOS = blogDao.selectNormalNotes();
        for(BlogDO blogDO : blogDOS){
            String title = blogDO.getTitle();
            String temp = "=#---\n" +
                    "title: "+blogDO.getTitle()+"\n" +
                    "category: 刷题日记\n" +
                    "tag:\n" +
                    "  - LeetCode\n" +
                    "head:\n" +
                    "  - - meta\n" +
                    "    - name: keywords\n" +
                    "      content: JVM,JDK,JRE,字节码详解,Java 基本数据类型,装箱和拆箱\n" +
                    "  - - meta\n" +
                    "    - name: description\n" +
                    "      content: 全网质量最高的Java基础常见知识点和面试题总结，希望对你有帮助！\n" +
                    "=#---\n";
            String blogContent = blogDO.getBlogContent();
            FileWriter writer = new FileWriter("D:/桌面文件/文章/正经笔记/"+title+".md");
            writer.write(temp+blogContent);
        }

    }
}
