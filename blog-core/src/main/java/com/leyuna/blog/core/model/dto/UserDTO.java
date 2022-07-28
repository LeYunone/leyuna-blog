package com.leyuna.blog.core.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author pengli
 * @create 2021-08-31 14:35
 *
 * 与前端交互的用户对象
 */
@Getter
@Setter
public class UserDTO {

    private String userName;

    private String passWord;
}
