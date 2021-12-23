package com.leyuna.blog.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Type)实体对象
 *
 * @author pengli
 * @since 2021-08-31 17:09:20
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("type")
public class Type implements Serializable {

    private static final long serialVersionUID = 157022049453539841L;

    /**
     * 标签id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

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
    private String fatherType;

}

