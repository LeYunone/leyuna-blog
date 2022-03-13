package com.leyuna.blog.bean.blog;

import com.leyuna.blog.bean.QueryPage;
import lombok.Getter;
import lombok.Setter;

/**
 * @author pengli
 * @create 2021-09-15 09:06
 */
@Getter
@Setter
public class CommentBean extends QueryPage {

    //评论内容
    private String content;
    
    private Integer type;

    //父评论编号
    private String fatherCommentId;

    //署名
    private String name;

    //头像
    private String commentHead;

    //联系方式
    private String information;

    //博客编号
    private String blogId;

    //被回复人
    private String respondent;

    private String ip;

    private String admin;

}
