package com.leyuna.blog.domain;

import com.leyuna.blog.co.WebHistoryCO;
import com.leyuna.blog.gateway.WebHistoryGateway;
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
 * (WebHistory) 工作台
 *
 * @author pengli
 * @since 2021-12-22 16:53:13
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
public class WebHistoryE implements Serializable {
    private static final long serialVersionUID = -43108340454261257L;

    private String id;

    private String title;

    private String content;

    private LocalDateTime createTime;

    //===========自定义方法区==========
    private WebHistoryGateway gateway;

    public WebHistoryGateway getGateway () {
        if (Objects.isNull(this.gateway)) {
            this.gateway = SpringContextUtil.getBean(WebHistoryGateway.class);
        }
        return this.gateway;
    }

    public static WebHistoryE queryInstance () {
        return new WebHistoryE();
    }

    public static WebHistoryE of (Object data) {
        return TransformationUtil.copyToDTO(data, WebHistoryE.class);
    }

    public List<WebHistoryCO> selectByCon () {
        WebHistoryGateway gateway = this.getGateway();
        return gateway.selectByCon(this);
    }

    public WebHistoryCO save () {
        WebHistoryGateway gateway = this.getGateway();
        return gateway.insertOrUpdate(this);
    }

    /**
     * 根据id查询
     */
    public WebHistoryCO selectById () {
        return this.getGateway().selectById(this.getId());
    }

    /**
     * 更新
     */
    public boolean update () {
        WebHistoryGateway gateway = this.getGateway();
        return gateway.update(this);
    }

    public static boolean batchCreate (List<WebHistoryE> list) {
        return WebHistoryE.queryInstance().getGateway().batchCreate(list);
    }
}
