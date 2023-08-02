package com.itbaizhan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 支付控制层
 */
@RestController
@RequestMapping("payment")
public class PaymentController
{
    /**
     * 测试服务调用
     * @return
     */
    @GetMapping("index")
    public String index()
    {
        try
        {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return "payment success";
    }


    /**
     * 测试超时机制
     * @return
     */
    @GetMapping("timeout")
    public String timeout()
    {
        try
        {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return "payment success timeout";
    }
}
