package com.blog.api.command;

import com.blog.api.dto.BlogDTO;
import com.blog.daoservice.dao.BlogDao;

/**
 * @author pengli
 * @create 2021-08-16 13:33
 *
 * 博客 操作指令
 */
public class BlogExe {

    private BlogDao blogDao;

    public boolean addBlog(BlogDTO blogDTO){
        return true;
    }
}
