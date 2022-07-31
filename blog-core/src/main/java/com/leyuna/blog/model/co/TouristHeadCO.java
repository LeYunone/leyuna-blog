package com.leyuna.blog.model.co;


import lombok.*;

import java.io.Serializable;

/**
 * (TouristHead)出参
 *
 * @author
 * @since 2021-09-17 11:15:11
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TouristHeadCO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 游客ip地址
     */
    private String ip;

    /**
     * 游客头像
     */
    private String head;
}

