package com.itbaizhan.controller;

import com.itbaizhan.service.PaymentOpenFeignService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController
{
    @Autowired
    private PaymentOpenFeignService paymentOpenFeignService;

    /**
     * 测试超时降级
     * @return
     */
    @GetMapping("timeout")
    @TimeLimiter(name = "delay", fallbackMethod = "timeoutfallback")
    public CompletableFuture timeout()
    {
        log.info("***** 进入方法 ******");

        // 异步操作
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync((Supplier<String>) () -> paymentOpenFeignService.timeout());

        log.info("***** 离开方法 ******");

        return stringCompletableFuture;
    }


    /**
     * 超时降级的方法
     * @param e
     * @return
     */
    public CompletableFuture<String> timeoutfallback(Exception e)
    {
        e.printStackTrace();
        return CompletableFuture.completedFuture("超时了");
    }

    /**
     * 重试机制
     * @return
     */
    @GetMapping("retry")
    @Retry(name = "backendA")
    public CompletableFuture<String> retry()
    {
        log.info("*********** 进入方法 *************");
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync((Supplier<String>) () -> paymentOpenFeignService.index());
        log.info("*********** 离开方法 *************");
        return stringCompletableFuture;
    }

    /**
     * 测试异常熔断机制
     * @return
     */
    @GetMapping("circuitbreaker")
    @CircuitBreaker(name = "backendA")
    public String circuitbreaker()
    {
        log.info("*********** 进入方法 *************");

        String index = paymentOpenFeignService.index();

        log.info("*********** 离开方法 *************");

        return index;
    }

    /**
     * 慢调用熔断降级
     * @return
     */
    @GetMapping("slowcircuitbreaker")
    @CircuitBreaker(name="backendB",fallbackMethod = "slowcircuitbreakerfallback")
    public String slowcircuitbreaker()
    {
        log.info("************* 进入方法 *************");

        String index = paymentOpenFeignService.index();
        log.info("************* 离开方法 *************");
        return index;
    }

    /**
     * 慢调用熔断降级
     * @return
     */
    public String slowcircuitbreakerfallback(Exception e)
    {
        e.printStackTrace();
        System.out.println("太慢了。。。。。");
        return "太慢了。。。。。";
    }

}
