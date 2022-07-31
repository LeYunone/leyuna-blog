//package com.leyuna.blog.system.listen;
//
//import com.leyuna.blog.load.ConstantLoad;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
///**
// * @author pengli
// * @create 2022-03-29 09:36
// * 项目启动时加载服务器参数至常数列表中
// */
//@Component
//public class ConstantInitRunner implements ApplicationRunner {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private ConstantLoad constantLoad;
//
//    @Override
//    public void run (ApplicationArguments args) {
//        //加载服务器内一般常数配置
//        constantLoad.loading();
//    }
//}
