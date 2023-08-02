package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 主启动类
 */
@SpringBootApplication
@Slf4j
@EnableFeignClients // 开启服务调用openfeign接口
public class OrderFeignMain80
{
    public static void main(String[] args)
    {
        SpringApplication.run(OrderFeignMain80.class,args);
        System.out.println("*********** OrderMain80 openFeign 启动成功 ****************");
    }
}
