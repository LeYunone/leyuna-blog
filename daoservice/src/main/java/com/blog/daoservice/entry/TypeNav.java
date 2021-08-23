package com.blog.daoservice.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.*;

/**
 * (TypeNav)实体对象
 *
 * @author pengli
 * @since 2021-08-23 13:34:40
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("type_nav")
public class TypeNav {

    private static final long serialVersionUID = -21673267096407635L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 导航名
     */
    @TableField(value = "type_nav_name")
    private String typeNavName;

}

