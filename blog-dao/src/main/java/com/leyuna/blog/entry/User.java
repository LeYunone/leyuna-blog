package com.leyuna.blog.entry;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * (User)实体对象
 *
 * @author pengli
 * @since 2021-08-31 17:09:34
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = -82046506637453159L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "pass_word")
    private String passWord;

}

