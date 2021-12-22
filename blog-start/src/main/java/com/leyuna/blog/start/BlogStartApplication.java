package com.leyuna.blog.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching //开启缓存
@ComponentScan({"com.leyuna.blog.*"})
public class BlogStartApplication extends SpringBootServletInitializer {

    public static void main (String[] args) {
        SpringApplication.run(BlogStartApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BlogStartApplication.class);
    }
}
