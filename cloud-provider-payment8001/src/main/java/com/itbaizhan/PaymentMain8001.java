package com.itbaizhan;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 主启动类
 */
@EnableEurekaClient // 把自己注册到eureka server上
@SpringBootApplication
@Slf4j
public class PaymentMain8001
{
    public static void main(String[] args)
    {
        SpringApplication.run(PaymentMain8001.class,args);
        log.info("********* PaymentMain8001 服务启动成功 **********");
    }
}
