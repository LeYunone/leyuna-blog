package com.blog.daoservice.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author pengli
 * @create 2021-08-10 14:43
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("tag")
public class Tag {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String tagName;

    private LocalDateTime createTime;

    private Integer useCount;

    private LocalDateTime lastUserTime;
}
