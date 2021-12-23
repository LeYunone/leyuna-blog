package com.leyuna.blog.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * (TypeNav)实体对象
 *
 * @author pengli
 * @since 2021-08-31 17:09:26
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("type_nav")
public class TypeNav implements Serializable {

    private static final long serialVersionUID = 530079104096721060L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 导航名
     */
    @TableField(value = "type_nav_name")
    private String typeNavName;

}

