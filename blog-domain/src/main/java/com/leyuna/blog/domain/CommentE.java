package com.leyuna.blog.domain;

import com.leyuna.blog.co.blog.CommentCO;
import com.leyuna.blog.gateway.CommentDao;
import com.leyuna.blog.util.SpringContextUtil;
import com.leyuna.blog.util.TransformationUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * (Comment) 工作台
 *
 * @author pengli
 * @since 2021-12-22 16:49:28
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentE implements Serializable {
    private static final long serialVersionUID = 253132168192267596L;

    private String id;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论时间
     */
    private LocalDateTime createDt;
    /**
     * 父评论编号
     */
    private String fatherCommentId;
    /**
     * 头像
     */
    private String commentHead;
    /**
     * 联系方式
     */
    private String information;
    /**
     * 联系人
     */
    private String name;
    /**
     * 点赞数
     */
    private Integer goods;
    /**
     * 评论人地址
     */
    private String ip;
    /**
     * 博客编号
     */
    private String blogId;
    /**
     * 被回复人名
     */
    private String respondent;

    private String admin;

    //===========自定义方法区==========
    private CommentDao gateway;

    public CommentDao getGateway () {
        if (Objects.isNull(this.gateway)) {
            this.gateway = SpringContextUtil.getBean(CommentDao.class);
        }
        return this.gateway;
    }

    public static CommentE queryInstance () {
        return new CommentE();
    }

    public static CommentE of (Object data) {
        return TransformationUtil.copyToDTO(data, CommentE.class);
    }

    public List<CommentCO> selectByCon () {
        CommentDao gateway = this.getGateway();
        return gateway.selectByCon(this);
    }

    public CommentCO save () {
        CommentDao gateway = this.getGateway();
        return gateway.insertOrUpdate(this);
    }

    /**
     * 根据id查询
     */
    public CommentCO selectById () {
        return this.getGateway().selectById(this.getId());
    }

    /**
     * 更新
     */
    public boolean update () {
        CommentDao gateway = this.getGateway();
        return gateway.update(this);
    }

    public static boolean batchCreate (List<CommentE> list) {
        return CommentE.queryInstance().getGateway().batchCreate(list);
    }
}
