package com.leyuna.blog.constant.code;

/**
 * @author pengli
 * @create 2021-08-20 15:02
 *
 * 系统操作异常参数
 */
public enum  SystemErrorEnum {

    ADD_BLOG_FAIL("发布失败",1),
    ADD_TYPENAV_FAIL("添加分类导航失败",2),
    ADD_TAG_FALE("添加标签错误",3),
    ADD_TYPE_FALE("添加分页错误",4),

    DELETE_TAG_FALE("删除标签错误",5),
    DELETE_TYPE_FALE("删除分页错误",6),

    UPDATE_TAG_FALE("更新标签失败",7),
    UPDATE_TYPE_FALE("更新分页失败",8),
    UPDATE_TYPENAV_FAIL("更新分类导航失败",9),
    UPDATE_BLOG_FAIL("更新博客失败",10),

    CREATE_DOCUMENT_FALE("构建文档索引失败",11),
    OBJECT_NULL("对象为空",12),

    DELETE_TYPENAV_FAIL("删除分类导航失败",13),

    UPLOCAD_IMG_FAIL("上传图片失败",14),

    QUERY_SEARCH("站内搜索失败",15),

    REQUEST_FAIL("请求失败,系统异常",16),

    COMMENT_FAIL("回复失败-系统故障",18),

    UPDOWN_IMG_FAIL("解析图片失败-系统故障",19),

    SELECT_NOT_FAIL("信息查询失败",20);

    private  String msg;
    private  Integer value;

    SystemErrorEnum(String msg,Integer value){
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
