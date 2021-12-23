package com.leyuna.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CloudServerApplication {

    public static void main (String[] args) {
        SpringApplication.run(CloudServerApplication.class, args);
    }

}
