package com.leyuna.blog.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author pengli
 * @create 2021-09-15 09:06
 */
@Getter
@Setter
public class CommentBean {

    //评论内容
    private String content;

    //父评论编号
    private Integer fatherCommentId;

    //署名
    private String name;

    //头像
    private String commentHead;

    //联系方式
    private String information;

    //博客编号
    private Integer blogId;

    //被回复人
    private String respondent;

    private String ip;

    private String admin;

}