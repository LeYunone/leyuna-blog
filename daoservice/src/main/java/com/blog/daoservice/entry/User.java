package com.blog.daoservice.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author pengli
 * @create 2021-08-10 14:43
 */
@Getter
@Setter
@ToString
@TableName("user")
public class User {

    @TableId(value = "user_name")
    private String userName;

    @TableId(value = "pass_word")
    private String passWord;
}
