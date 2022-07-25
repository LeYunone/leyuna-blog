package com.leyuna.blog.core.constant.enums;

/**
 * @author pengli
 * @create 2022-03-25 09:56
 * 上传图片的类型
 */
public enum UploadImgTypeEnum {

    IMAGE_IMG("1", "普通图片"),

    EMO_IMG("2", "表情包");


    private String value;

    private String name;

    UploadImgTypeEnum (String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue () {
        return this.value;
    }

    public String getName () {
        return this.name;
    }

}
