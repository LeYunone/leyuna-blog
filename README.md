# Leyuna-Blog

本博客目前由SpringBoot+Mybatis-plus+Redis+Sa-Token+Nacos+FeignClient+....等技术前后端分离实现，前端Vue3+Element Ui亲手画成，前台页面[Blog-html-face](https://github.com/LeYunone/blog-html-face)，后台页面[Blog-html](https://github.com/LeYunone/blog-html)

。目前已完成个人博客的所有基础功能：发布 - 查看 - 管理 - ...等。挂彩功能：博客评论，装载基于Lucene搜索框架的站内搜索、挂载在网站上的 [自开发云盘](https://github.com/LeYunone/leyuna-disk)，均为开源，并且还在1.0.0阶段，未来将对网站不断优化和填充新功能。

![](https://camo.githubusercontent.com/085c765408b9917977576b09461919e378e51975c7ac2c30e64b48368a386c32/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f76657273696f6e2d312e302e302d79656c6c6f772e737667)

## 快速启动

在数据库中运行以下两文件

[blog数据库](https://github.com/LeYunone/leyuna-blog/blob/master/blogStructure.sql)

[disk数据库](https://github.com/LeYunone/leyuna-blog/blob/master/diskStructure.sql)

在**blog-start** 模块中，找到**application.properties**、**application.yml**、**bootstrap.properties**

按顺序设置配置。

```properties
# REDIS (RedisProperties)
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=127.0.0.1
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
#spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.poolMaxActive=20
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.poolMaxWait=-1
# 连接池中的最大空闲连接
spring.redis.poolMaxIdle=8
# 连接池中的最小空闲连接
spring.redis.poolMinIdle=0
# 连接超时时间（毫秒）
spring.redis.timeout=5000
spring.application.name=LEYUNA-BLOG
# 熔断器配置
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
feign.hystrix.enabled=true
# feign客户端配置
feign.client.config.default.connect-timeout=50000
feign.client.config.default.read-timeout=50000
##服务器常数
SERVER_HEAD_IMG_ADDR=头像存储域名
SERVER_HEAD_IMG_DEFAULT=默认头像
SERVER_HEAD_IMG_ADMIN=站长头像
SERVER_IMG_SAVE_PATH=文件存储域名
SERVER_EMO_SAVE_PATH=表情包存储域名
DIR_SAVE_PATH=搜索库地址
EMO_SAVE_PATH=表情包地址
IMG_SAVE_PATH=图片包地址
SERVER_NAME=服务器名
```

```properties
## 数据库配置
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/blog?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false
    username: root
    password: root
    filters: stat
    maxActive: 20
    initialSize: 1
    maxWait: 60000
    minIdle: 1
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: select 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    maxOpenPreparedStatements: 20
    driver-class-name: com.mysql.jdbc.Driver
  servlet:
    multipart:
      max-file-size: -1
      max-request-size: -1
## 接口配置
server:
  servlet:
    context-path: '/leyuna'

## 打印日志级别
logging:
  level:
    com.chz.mapper: debug
### mp日志
#mybatis-plus:
#  configuration:Dom4j
#    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl #开启sql日志



```

```properties
##nacos配置
spring.application.name=LEYUNA-BLOG

spring.cloud.nacos.discovery.server-addr=localhost:8848
spring.cloud.nacos.config.server-addr=localhost:8848
spring.cloud.nacos.config.file-extension=properties
spring.cloud.nacos.config.prefix=LEYUNA-BLOG
spring.profiles.active=dev
server.port=9000
```

## 当前功能介绍

### 博客：

因为博客功能基本人尽皆知了，所以以下功能点均带过。

#### 发布博客

启动后台页面blog-html,端口8888.

![](https://www.leyuna.xyz/image/2022-03-29/image.png)

目前发布内容支持：普通文章和网站公告，未来衍生：小说、动漫、电影测评，每月爬虫结果报告等...

使用的是 [v-md-editor](http://ckang1229.gitee.io/vue-markdown-editor/zh/) Markdown语法的编辑器。

目前支持：markdown语法的基本编辑，文件上传和回显功能，表情包，emoJI表情...

#### 管理内容

![](https://www.leyuna.xyz/image/2022-03-29/企业微信截图_20220329153924.png)

crud基本功能不多提

#### 查看博客

打开前台页面blog-html-face，端口8000。

![](https://www.leyuna.xyz/image/2022-03-29/企业微信截图_20220329154240.png)

![](https://www.leyuna.xyz/image/2022-03-29/企业微信截图_20220329154401.png)

2020-03-29 版本1.0.5 页面，后续将重大改造，当前为简洁风.

目录导航[可点击跳动]和角色卡切换

#### 评论

![](https://www.leyuna.xyz/image/2022-03-29/企业微信截图_20220329154735.png)

2022-03-29 1.0.5版本，当前评论采取的是伪一人一号的概念，不需要注册账号即可评论，并且限制评论数目、时间和点赞

#### 站内搜索引擎

![](https://www.leyuna.xyz/image/2022-03-29/企业微信截图_20220329160651.png)

逐字动态搜索的功能，非常好用，优化过效率，所以在站内博客不超过十万级数据前速度均可接受

### 云盘

 [自开发云盘](https://github.com/LeYunone/leyuna-disk)

## 更新日志：

**2021-09-07：** 1.0.0 博客基本功能上线

**2021-09-11：** 基于Lucene的站内搜索

**2021-09-18：** 评论功能上线

**2021-12-24：** 项目模块重构，加入各类抽象概念

**2022-03-02：** 挂载微服务：自开发云盘

**2022-03-24:**    Markdown编辑加入emoJi表情和表情包系统

## 最后

博客开发还未完成...，有兴趣的可以一起研究新世纪网站的新功能。

![](https://www.leyuna.xyz/image/avatar/qq.jpg)