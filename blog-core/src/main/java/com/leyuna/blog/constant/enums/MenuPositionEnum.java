package com.leyuna.blog.constant.enums;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-26
 */
public enum MenuPositionEnum {

    NAV_MENU(0, "普通目录"),

    ARTICLE_MENU(1, "文章目录");

    private Integer code;

    private String remark;

    MenuPositionEnum(Integer code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
