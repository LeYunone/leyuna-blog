package com.leyuna.blog.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author
 * @date
 */
@Component
public class BlogMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //更新时间自动装配
        this.setFieldValByName("updateDt", LocalDateTime.now(), metaObject);
        //创建时间自动装配
        this.setFieldValByName("createDt", LocalDateTime.now(), metaObject);
        //如果字段有deleted 打上0标识
        this.setFieldValByName("deleted", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新时更新更新时间
        this.setFieldValByName("updateDt", LocalDateTime.now(), metaObject);
    }
}
