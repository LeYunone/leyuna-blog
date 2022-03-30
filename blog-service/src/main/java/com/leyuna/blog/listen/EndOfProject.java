package com.leyuna.blog.listen;

import com.leyuna.blog.command.CacheExe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pengli
 * @create 2022-02-28 09:40
 * 项目结束时的清零操作
 */
@Component
public class EndOfProject implements DisposableBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CacheExe cacheExe;

    @Override
    public void destroy () {
        //清空缓存
        cacheExe.clearAllCache();
    }
}
