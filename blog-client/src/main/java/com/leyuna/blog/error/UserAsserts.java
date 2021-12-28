package com.leyuna.blog.error;

/**
 * @author pengli
 * @create 2021-08-10 19:35
 * 用户操作错误参数
 */
public enum  UserAsserts {

    LOGINT_FAIL("登录失败，用户名或密码错误",1),

    FILE_ALl_TOO_OFTEN("今天上传的机会没了，明天再来吧",2),

    GOODS_COMMENT_FAIL("今天的这次点赞已经用完了，明天再来吧",3),
    REQUEST_FREQUENTLY_FAIL("说慢一点，我缓一下",4);

    private final String msg;
    private Integer value;
    UserAsserts(String msg,Integer value){
        this.msg=msg;
        this.value=value;
    }
    public String getMsg(){
        return this.msg;
    }
    public Integer getValue(){
        return this.value;
    }
    public String toString(){
        return this.msg;
    }
}
