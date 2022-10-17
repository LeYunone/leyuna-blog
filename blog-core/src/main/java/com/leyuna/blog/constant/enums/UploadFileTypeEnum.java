package com.leyuna.blog.constant.enums;

/**
 * @author pengli
 * @create 2022-03-25 09:56
 * 上传图片的类型
 */
public enum UploadFileTypeEnum {

    COMMON_IMG(1, "普通文件", "commonFile"),

    EMO_IMG(2, "表情包", "emoFile");


    private Integer code;

    private String name;

    /**
     * 文件服务类
     */
    private String fileService;

    UploadFileTypeEnum(Integer code, String name, String fileService) {
        this.code = code;
        this.name = name;
        this.fileService = fileService;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
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

    public static UploadFileTypeEnum getEnum(Integer type) {
        UploadFileTypeEnum[] values = UploadFileTypeEnum.values();
        for (UploadFileTypeEnum uploadFileTypeEnum : values) {
            if(uploadFileTypeEnum.getCode().equals(type)){
                return uploadFileTypeEnum;
            }
        }
        return null;
    }
}
