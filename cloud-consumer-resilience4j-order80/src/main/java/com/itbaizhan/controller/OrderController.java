package com.itbaizhan.controller;

import com.itbaizhan.service.PaymentOpenFeignService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController
{
    @Autowired
    private PaymentOpenFeignService paymentOpenFeignService;

    /**
     * 测试超时降级
     * @return
     */
    @GetMapping("/timeout")
    @TimeLimiter(name = "delay", fallbackMethod = "timeoutfallback")
    public CompletableFuture timeout()
    {
        log.info("***** 进入方法 ******");
        Date start = new Date();


        // 异步操作 为了可以看到超时的效果
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync((Supplier<String>) () -> paymentOpenFeignService.timeout());

        Date end = new Date();

        long interval=(end.getTime()-start.getTime());
        System.out.println("用时："+interval+"毫秒");
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
    @GetMapping("/retry")
    @Retry(name = "backendA")
    public CompletableFuture<String> retry() {
        log.info("********* 进入方法 ******");
        //异步操作
        CompletableFuture<String> completableFuture = CompletableFuture
                .supplyAsync((Supplier<String>) () -> (paymentOpenFeignService.index()));
        log.info("********* 离开方法 ******");
        return completableFuture;
    }


    /**
     * 测试异常熔断机制
     * @return
     */
    @GetMapping("/circuitbreaker")
    @CircuitBreaker(name = "backendA",fallbackMethod = "circuitbreakerfallback")
    public String circuitbreaker()
    {
        log.info("*********** 进入方法 *************");

        String index = paymentOpenFeignService.index();

        log.info("*********** 离开方法 *************");

        return index;
    }

    public String circuitbreakerfallback(Exception e){
        e.printStackTrace();
        return "服务繁忙，稍等一会";
    }

    /**
     * 慢调用熔断降级
     * @return
     */
    @GetMapping("/slowcircuitbreaker")
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


    /**
     * 测试信号量隔离
     * @return
     */
    @Bulkhead(name = "backendA",type = Bulkhead.Type.SEMAPHORE)
    @GetMapping("bulkhead")
    public String bulkhead() throws InterruptedException {
        log.info("************** 进入方法 *******");
        TimeUnit.SECONDS.sleep(10);
        String index = paymentOpenFeignService.index();
        log.info("************** 离开方法 *******");
        return index;
    }


    /**
     * 测试线程池服务隔离
     * @return
     */
    @Bulkhead(name = "backendA",type = Bulkhead.Type.THREADPOOL)
    @GetMapping("/future")
    public CompletableFuture future(){
        log.info("********** 进入方法 *******");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("********** 离开方法 *******");
        return CompletableFuture.supplyAsync(() -> "线程池隔离信息......");
    }


    /**
     * 限流
     * @return
     */
    @GetMapping("/limiter")
    @RateLimiter(name = "backendA")
    public CompletableFuture<String> RateLimiter() {
        log.info("********* 进入方法 ******");
        //异步操作
        CompletableFuture<String> completableFuture = CompletableFuture
                .supplyAsync((Supplier<String>) () -> (paymentOpenFeignService.index()));
        log.info("********* 离开方法 ******");
        return completableFuture;
    }



}
