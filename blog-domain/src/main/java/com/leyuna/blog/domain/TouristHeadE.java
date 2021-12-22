package com.leyuna.blog.domain;

import com.leyuna.blog.co.TouristHeadCO;
import com.leyuna.blog.gateway.TouristHeadGateway;
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
 * (TouristHead) 工作台
 *
 * @author pengli
 * @since 2021-12-22 16:52:01
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TouristHeadE implements Serializable {
    private static final long serialVersionUID = -27580023173626327L;

    private String id;
    /**
     * 游客ip地址
     */
    private String ip;
    /**
     * 游客头像
     */
    private String head;

    //===========自定义方法区==========
    private TouristHeadGateway gateway;

    public TouristHeadGateway getGateway () {
        if (Objects.isNull(this.gateway)) {
            this.gateway = SpringContextUtil.getBean(TouristHeadGateway.class);
        }
        return this.gateway;
    }

    public static TouristHeadE queryInstance () {
        return new TouristHeadE();
    }

    public static TouristHeadE of (Object data) {
        return TransformationUtil.copyToDTO(data, TouristHeadE.class);
    }

    public List<TouristHeadCO> selectByCon () {
        TouristHeadGateway gateway = this.getGateway();
        return gateway.selectByCon(this);
    }

    public TouristHeadCO save () {
        TouristHeadGateway gateway = this.getGateway();
        return gateway.insertOrUpdate(this);
    }

    /**
     * 根据id查询
     */
    public TouristHeadCO selectById () {
        return this.getGateway().selectById(this.getId());
    }

    /**
     * 更新
     */
    public boolean update () {
        TouristHeadGateway gateway = this.getGateway();
        return gateway.update(this);
    }

    public static boolean batchCreate (List<TouristHeadE> list) {
        return TouristHeadE.queryInstance().getGateway().batchCreate(list);
    }
}
