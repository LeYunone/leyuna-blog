package com.blog.daoservice.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.*;

import java.time.LocalDateTime;

/**
 * (Type)实体对象
 *
 * @author pengli
 * @since 2021-08-23 13:54:35
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("type")
public class Type {

    private static final long serialVersionUID = 382747161178443803L;

    /**
     * 标签id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签名
     */
    @TableField(value = "type_name")
    private String typeName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 使用次数
     */
    @TableField(value = "use_count")
    private Integer useCount;

    /**
     * 最后使用时间
     */
    @TableField(value = "last_user_time")
    private LocalDateTime lastUserTime;

    /**
     * 分类导航
     */
    @TableField(value = "father_type")
    private Integer fatherType;

}

