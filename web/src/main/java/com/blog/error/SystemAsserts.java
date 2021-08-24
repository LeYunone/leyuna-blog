package com.blog.error;

/**
 * @author pengli
 * @create 2021-08-20 15:02
 *
 * 系统操作异常参数
 */
public enum  SystemAsserts {

    ADD_BLOG_FAIL("发布失败:"),
    ADD_TYPENAV_FAIL("添加分类导航失败"),

    UPDATE_TYPENAV_FAIL("更新分类导航失败");
    private final String msg;

    SystemAsserts(String msg){
        this.msg=msg;
    }
    public String getMsg(){
        return this.msg;
    }

    public String toString(){
        return this.msg;
    }
}
