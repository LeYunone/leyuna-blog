package com.leyuna.blog.domain;

import com.leyuna.blog.co.TagCO;
import com.leyuna.blog.gateway.TagGateway;
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
 * (Tag) 工作台
 *
 * @author pengli
 * @since 2021-12-22 16:51:39
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TagE implements Serializable {
    private static final long serialVersionUID = 550073419473362434L;

    private String id;

    private String tagName;

    private LocalDateTime createTime;

    private Integer useCount;

    private LocalDateTime lastUserTime;

    //===========自定义方法区==========
    private TagGateway gateway;

    public TagGateway getGateway () {
        if (Objects.isNull(this.gateway)) {
            this.gateway = SpringContextUtil.getBean(TagGateway.class);
        }
        return this.gateway;
    }

    public static TagE queryInstance () {
        return new TagE();
    }

    public static TagE of (Object data) {
        return TransformationUtil.copyToDTO(data, TagE.class);
    }

    public List<TagCO> selectByCon () {
        TagGateway gateway = this.getGateway();
        return gateway.selectByCon(this);
    }

    public TagCO save () {
        TagGateway gateway = this.getGateway();
        return gateway.insertOrUpdate(this);
    }

    /**
     * 根据id查询
     */
    public TagCO selectById () {
        return this.getGateway().selectById(this.getId());
    }

    /**
     * 更新
     */
    public boolean update () {
        TagGateway gateway = this.getGateway();
        return gateway.update(this);
    }

    public static boolean batchCreate (List<TagE> list) {
        return TagE.queryInstance().getGateway().batchCreate(list);
    }
}
