package com.blog.daoservice.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

/**
 * (TouristHead)实体对象
 *
 * @author
 * @since 2021-09-17 11:15:03
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("tourist_head")
public class TouristHead implements Serializable {

    private static final long serialVersionUID = 544200023655916831L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 游客ip地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 游客头像
     */
    @TableField(value = "head")
    private String head;

}

