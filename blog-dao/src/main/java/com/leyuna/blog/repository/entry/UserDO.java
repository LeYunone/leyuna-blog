package com.leyuna.blog.repository.entry;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Getter;
import lombok.Setter;

/**
 * (User)表实体类
 *
 * @author pengli
 * @since 2022-03-15 17:04:25
 */
@Getter
@Setter
@TableName("user")
public class UserDO implements Serializable {
    private static final long serialVersionUID = 617791215679106594L;
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

}
