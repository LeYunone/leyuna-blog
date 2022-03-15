package com.leyuna.blog.repository.entry;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Getter;
import lombok.Setter;

/**
 * (TouristHead)表实体类
 *
 * @author pengli
 * @since 2022-03-15 17:04:24
 */
@Getter
@Setter
@TableName("tourist_head")
public class TouristHeadDO implements Serializable {
    private static final long serialVersionUID = 851632183581476705L;
    private String id;

    /**
     * 游客ip地址
     */
    private String ip;

    /**
     * 游客头像
     */
    private String head;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

}
