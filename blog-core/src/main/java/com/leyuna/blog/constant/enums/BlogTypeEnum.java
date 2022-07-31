package com.leyuna.blog.constant.enums;

/**
 * @author pengli
 * @date 2022-03-14
 * 博客类型
 */
public enum BlogTypeEnum {

    BLOG_TYPE(1,"博客"),
    NOTICE_TYPE(2,"公告"),
    MY_TYPE(3,"个人"),
    ANIME_TYPE(4,"番剧"),
    ARTICLE_TYPE(5,"文章");


    private Integer value;

    private String name;

    BlogTypeEnum(Integer value,String name){
        this.value=value;
        this.name=name;
    }

    public Integer getValue(){
        return this.value;
    }

    public String getName(){
        return this.name;
    }

    public static BlogTypeEnum loadName(Integer value){
        BlogTypeEnum[] values = values();
        for (BlogTypeEnum enums: values){
            if(enums.getValue().equals(value)){
                return enums;
            }
        }
        return null;
    }
}
