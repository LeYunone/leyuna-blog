package com.blog.web.bean;

import lombok.*;

import java.util.List;

/**
 * @author pengli
 * @create 2021-08-10 15:05
 *   一般响应结果集
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseBean {
    //字符串结果集
    private String srcData;
    //对象结果集
    private Object objData;
    //对象结果集数组
    private Object[] objDatas;
    //对象结果集集合
    private List<Object> listData;
    //响应参数
    private Integer code;
}
