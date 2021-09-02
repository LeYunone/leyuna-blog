package com.blog.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author pengli
 * @create 2021-08-10 16:09
 *
 * user的出入参
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {

    private Integer id;

    private String userName;

    private String passWord;
}
