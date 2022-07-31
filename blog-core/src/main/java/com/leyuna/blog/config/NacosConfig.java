//package com.leyuna.blog.config;
//
//import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import javax.management.MBeanServer;
//import javax.management.ObjectName;
//import javax.management.Query;
//import java.lang.management.ManagementFactory;
//import java.util.Set;
//
///**
// * @author pengli
// * @create 2022-02-15 17:11
// * nacos配置，在外部tomcat中获取到启用的tomcat
// */
//@Component
//public class NacosConfig implements ApplicationRunner {
//
//    @Autowired(required = false)
//    private NacosAutoServiceRegistration registration;
//
//    @Value("${server.port}")
//    Integer port;
//
//    @Override
//    public void run (ApplicationArguments args) throws Exception {
//        if (registration != null && port != null) {
//            Integer tomcatPort = port;
//            try {
//                tomcatPort = new Integer(getTomcatPort());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            registration.setPort(tomcatPort);
//            registration.start();
//        }
//    }
//
//    public String getTomcatPort() throws Exception {
//        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
//        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
//        String port = objectNames.iterator().next().getKeyProperty("port");
//        return port;
//    }
//}
