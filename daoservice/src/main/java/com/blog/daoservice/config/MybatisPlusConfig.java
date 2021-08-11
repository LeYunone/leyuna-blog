package com.blog.daoservice.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengli
 * @date
 */
@Configuration
@MapperScan({"com.blog.daoservice.mapper"})
public class MybatisPlusConfig {

}
