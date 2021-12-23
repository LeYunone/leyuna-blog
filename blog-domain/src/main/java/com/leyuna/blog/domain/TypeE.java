package com.leyuna.blog.domain;

import com.leyuna.blog.co.TypeCO;
import com.leyuna.blog.gateway.TypeGateway;
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
 * (Type) 工作台
 *
 * @author pengli
 * @since 2021-12-22 16:52:21
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TypeE implements Serializable {
    private static final long serialVersionUID = 309105804936846544L;
    /**
     * 标签id
     */
    private String id;
    /**
     * 标签名
     */
    private String typeName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 使用次数
     */
    private Integer useCount;
    /**
     * 最后使用时间
     */
    private LocalDateTime lastUserTime;
    /**
     * 分类导航
     */
    private String fatherType;

    //===========自定义方法区==========
    private TypeGateway gateway;

    public TypeGateway getGateway () {
        if (Objects.isNull(this.gateway)) {
            this.gateway = SpringContextUtil.getBean(TypeGateway.class);
        }
        return this.gateway;
    }

    public static TypeE queryInstance () {
        return new TypeE();
    }

    public static TypeE of (Object data) {
        return TransformationUtil.copyToDTO(data, TypeE.class);
    }

    public List<TypeCO> selectByCon () {
        TypeGateway gateway = this.getGateway();
        return gateway.selectByCon(this);
    }

    public TypeCO save () {
        TypeGateway gateway = this.getGateway();
        return gateway.insertOrUpdate(this);
    }

    /**
     * 根据id查询
     */
    public TypeCO selectById () {
        return this.getGateway().selectById(this.getId());
    }

    /**
     * 更新
     */
    public boolean update () {
        TypeGateway gateway = this.getGateway();
        return gateway.update(this);
    }

    public static boolean batchCreate (List<TypeE> list) {
        return TypeE.queryInstance().getGateway().batchCreate(list);
    }
}
