package com.blog.daoservice.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

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
@TableName("user")
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableId(value = "user_name")
    private String userName;

    @TableId(value = "pass_word")
    private String passWord;
}
