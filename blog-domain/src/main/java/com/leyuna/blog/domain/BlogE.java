package com.leyuna.blog.domain;

import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.gateway.BlogDao;
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
 * (BlogDO) 工作台
 *
 * @author pengli
 * @since 2021-12-22 16:26:59
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogE implements Serializable {
    private static final long serialVersionUID = 634037375253558765L;
    /**
     * 博客编号
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 发布时间
     */
    private LocalDateTime createDt;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 内容
     */
    private String blogContent;
    /**
     * 分类
     */
    private String type;
    /**
     * 封面
     */
    private String blogCover;
    /**
     * 标签
     */
    private String tag;
    /**
     * 更新时间
     */
    private LocalDateTime updateDt;
    /**
     * 备注前言
     */
    private String remarks;

    /**
     * 博客类型
     */
    private Integer blogType;

    /**
     * 第三方链接
     */
    private String blogLink;

    //===========自定义方法区==========
    private BlogDao gateway;

    public BlogDao getGateway () {
        if (Objects.isNull(this.gateway)) {
            this.gateway = SpringContextUtil.getBean(BlogDao.class);
        }
        return this.gateway;
    }

    public static BlogE queryInstance () {
        return new BlogE();
    }

    public static BlogE of (Object data) {
        return TransformationUtil.copyToDTO(data, BlogE.class);
    }

    public List<BlogCO> selectByCon () {
        BlogDao gateway = this.getGateway();
        return gateway.selectByCon(this);
    }

    public BlogCO save () {
        BlogDao gateway = this.getGateway();
        return gateway.insertOrUpdate(this);
    }

    /**
     * 根据id查询
     */
    public BlogCO selectById () {
        return this.getGateway().selectById(this.getId());
    }

    /**
     * 更新
     */
    public boolean update () {
        BlogDao gateway = this.getGateway();
        return gateway.update(this);
    }

    public static boolean batchCreate (List<BlogE> list) {
        return BlogE.queryInstance().getGateway().batchCreate(list);
    }

    /**
     * 随机查询
     * @return
     */
    public static List<BlogCO> selectRandomList(){
        return BlogE.queryInstance().getGateway().selectRandomList();
    }
}
