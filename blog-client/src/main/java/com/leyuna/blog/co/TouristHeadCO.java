package com.leyuna.blog.co;


import lombok.*;

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
public class TouristHeadCO {


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

