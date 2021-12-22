package com.leyuna.blog.co;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author pengli
 * @create 2021-08-12 13:25
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagCO implements Comparable<TagCO>, Serializable {

    /**
     * 标签id
     */
    private String id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 创建时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    /**
     * 使用次数
     */
    private Integer useCount;

    private LocalDateTime lastUserTime;

    private String userStatus;

    @Override
    public int compareTo(TagCO o) {
        if(o.getTagName().length()>this.getTagName().length()){
            return -1;
        }else if(o.getTagName().length()<this.getTagName().length()){
            return 1;
        }else{
            return 0;
        }
    }
}