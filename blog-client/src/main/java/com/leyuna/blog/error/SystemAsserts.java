package com.leyuna.blog.error;

/**
 * @author pengli
 * @create 2021-08-20 15:02
 *
 * 系统操作异常参数
 */
public enum  SystemAsserts {

    ADD_BLOG_FAIL("发布失败:"),
    ADD_TYPENAV_FAIL("添加分类导航失败"),

    UPDATE_TYPENAV_FAIL("更新分类导航失败"),
    UPDATE_BLOG_FAIL("更新博客失败"),

    DELETE_TYPENAV_FAIL("删除分类导航失败"),

    UPLOCAD_IMG_FAIL("上传图片失败"),

    QUERY_SEARCH("站内搜索失败"),

    REQUEST_FAIL("请求失败"),
    REQUEST_FREQUENTLY_FAIL("说慢一点，我缓一下"),

    COMMENT_FAIL("回复失败-系统故障"),

    UPDOWN_IMG_FAIL("解析图片失败-系统故障");

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
