package com.blog.api.dto;

import lombok.*;

/**
 * @author pengli
 * @create 2021-08-13 14:38
 *
 * 返回前端结果返回集
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDTO {

    /**
     * 返回编码
     */
    private boolean code;

    /**
     * 信息
     */
    private String message;

    /**
     * 信息集合
     */
    private StringBuilder messages=null;

    public void addMessage(String str){
        if(this.messages==null){
            this.messages=new StringBuilder(str);
        }else{
            this.messages.append(str);
        }
    }
}
