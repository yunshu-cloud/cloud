package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 主启动类
 */
@EnableEurekaServer //开启eureka 服务注解
@SpringBootApplication
@Slf4j
public class EurekaServerMain7001
{
    public static void main(String[] args)
    {
        SpringApplication.run(EurekaServerMain7001.class,args);
        log.info("********* Eureka服务启动成功 **************");
    }
}
