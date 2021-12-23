package com.leyuna.blog.constant;

/**
 * @author pengli
 * @create 2021-08-13 15:07
 *
 * 响应control的编码
 */
public enum  ResponseCode {
    SUCCESS("200", "操作成功"),
    ERROR("404", "操作失败");

    private final String code;
    private final String desc;

    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
