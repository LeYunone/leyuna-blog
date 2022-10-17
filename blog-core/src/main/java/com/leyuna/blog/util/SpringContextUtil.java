//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.leyuna.blog.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private SpringContextUtil (ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static void initialize(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = applicationContext.getBean(clz);
        return result;
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    @Override
    public void setApplicationContext (ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }
}
