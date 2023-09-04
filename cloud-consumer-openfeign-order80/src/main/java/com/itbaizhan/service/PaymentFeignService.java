package com.itbaizhan.service;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 支付远程调用接口openfeign接口
 */

// 声明openfeign客户端
@FeignClient("APPLICATION-PROVIDER")  // 生命调用服务生产者的名字

public interface PaymentFeignService
{

    @GetMapping("/payment/index") //远程主机上微服务的地址
    String index();

    @GetMapping("/payment/timeout")
    String timeout();

}
