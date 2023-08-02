package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 主启动类
 */
@SpringBootApplication
@Slf4j
@EnableEurekaClient // 开启注册中心
public class OrderMain80
{
    public static void main(String[] args)
    {
        SpringApplication.run(OrderMain80.class,args);
        log.info("************ OrderMain80 服务启动成功 *************");
    }
}
