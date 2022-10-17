package com.leyuna.blog.constant.enums;

import cn.hutool.core.util.StrUtil;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-10-12
 */
public enum MenuUrlEnum {

    ARTICLE_URL("/article/","文章URL");

    private String url;

    private String remark;

    MenuUrlEnum(String url,String remark){
        this.url = url;
        this.remark = remark;
    }

    public String getUrl(String target) {
        if(StrUtil.isNotBlank(target)){
            url = url+target;
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
