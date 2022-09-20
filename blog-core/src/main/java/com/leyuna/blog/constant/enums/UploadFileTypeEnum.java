package com.leyuna.blog.constant.enums;

/**
 * @author pengli
 * @create 2022-03-25 09:56
 * 上传图片的类型
 */
public enum UploadFileTypeEnum {

    COMMON_IMG("1", "普通文件","commonFile"),

    EMO_IMG("2", "表情包","emoFile");


    private String value;

    private String name;

    /**
     * 文件服务类
     */
    private String fileService;

    UploadFileTypeEnum (String value, String name,String fileService) {
        this.value = value;
        this.name = name;
        this.fileService = fileService;
    }

    public String getValue () {
        return this.value;
    }

    public String getName () {
        return this.name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileService() {
        return fileService;
    }

    public void setFileService(String fileService) {
        this.fileService = fileService;
    }
}
