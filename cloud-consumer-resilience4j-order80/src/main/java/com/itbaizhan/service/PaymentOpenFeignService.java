package com.itbaizhan.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 支付远程调用接口openfeign接口
 */

// 声明openfeign客户端
@FeignClient("CLOUD-PAYMENT-PROVIDER")  // 生命调用服务生产者的名字
public interface PaymentOpenFeignService
{
    @GetMapping("/payment/timeout")
    String timeout();

    @GetMapping("/payment/index")
    String index();
}
