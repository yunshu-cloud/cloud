package com.itbaizhan.controller;

import com.itbaizhan.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制层
 */
@RestController
@RequestMapping("/order")
public class OrderController
{
    /**
     * 支付接口
     */
    @Autowired
    private PaymentFeignService paymentFeignService;
    /**
     * openfeign 远程服务调用
     * @return
     */
    @GetMapping("/index")
    public String index()
    {
        return paymentFeignService.index();
    }

    /**
     * 测试超时机制
     * @return
     */
    @GetMapping("/timeout")
    public String timeout(){
        return paymentFeignService.timeout();
    }
}
