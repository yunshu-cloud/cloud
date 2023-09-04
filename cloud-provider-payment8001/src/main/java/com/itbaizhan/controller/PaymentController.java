package com.itbaizhan.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 支付控制层
 */
@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController
{
    /**
     * 测试服务调用
     * @return
     */
    @GetMapping("/index")
    public String index()
    {
        return "payment success";
    }


    /**
     * 测试超时机制
     * @return
     */
    @GetMapping("/timeout")
    public String timeout()
    {
        return "payment success timeout";
    }
}
