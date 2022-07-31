package com.leyuna.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching //开启缓存
//@EnableFeignClients(basePackages = "com.leyuna.blog.rpc.service") //RPC接口扫描
@MapperScan("com.leyuna.blog.dao.repository.mapper")
public class BlogStartApplication extends SpringBootServletInitializer {

    public static void main (String[] args) {
        SpringApplication.run(BlogStartApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BlogStartApplication.class);
    }
}
