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

        this.setFieldValByName("updateDt", LocalDateTime.now(), metaObject);

        this.setFieldValByName("createDt", LocalDateTime.now(), metaObject);

        this.setFieldValByName("deleted", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updateDt", LocalDateTime.now(), metaObject);
    }
}
