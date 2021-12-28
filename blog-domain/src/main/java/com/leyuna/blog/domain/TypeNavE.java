package com.leyuna.blog.domain;

import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.gateway.TypeNavGateway;
import com.leyuna.blog.util.SpringContextUtil;
import com.leyuna.blog.util.TransformationUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * (TypeNav) 工作台
 *
 * @author pengli
 * @since 2021-12-22 16:52:39
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TypeNavE implements Serializable {
    private static final long serialVersionUID = -28543847775884977L;

    private String id;
    /**
     * 导航名
     */
    private String typeNavName;

    //===========自定义方法区==========
    private TypeNavGateway gateway;

    public TypeNavGateway getGateway () {
        if (Objects.isNull(this.gateway)) {
            this.gateway = SpringContextUtil.getBean(TypeNavGateway.class);
        }
        return this.gateway;
    }

    public static TypeNavE queryInstance () {
        return new TypeNavE();
    }

    public static TypeNavE of (Object data) {
        return TransformationUtil.copyToDTO(data, TypeNavE.class);
    }

    public List<TypeNavCO> selectByCon () {
        TypeNavGateway gateway = this.getGateway();
        return gateway.selectByCon(this);
    }

    public TypeNavCO save () {
        TypeNavGateway gateway = this.getGateway();
        return gateway.insertOrUpdate(this);
    }

    /**
     * 根据id查询
     */
    public TypeNavCO selectById () {
        return this.getGateway().selectById(this.getId());
    }

    /**
     * 更新
     */
    public boolean update () {
        TypeNavGateway gateway = this.getGateway();
        return gateway.update(this);
    }

    public static boolean batchCreate (List<TypeNavE> list) {
        return TypeNavE.queryInstance().getGateway().batchCreate(list);
    }
}
