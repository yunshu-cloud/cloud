package com.itbaizhan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController
{
    // 服务发现
    @Autowired
    private DiscoveryClient discoveryClient;

    // 服务调用
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取服务列表清单
     * @return
     */
    @GetMapping("discovery")
    public Object testDiscoveryClient()
    {
        List<String> services = discoveryClient.getServices();
        for (String service :
                services)
        {
            System.out.println(service);
        }
        return this.discoveryClient;
    }


    /**
     * 测试服务调用
     * @return
     */
    @GetMapping("index")
    public String index()
    {

        // 服务生产者名字
        String hostName = "APPLICATION-PROVIDER";

        // 远程调用方法具体的URL
        String url = "/payment/index";
        String host = "http://application-provider";
//        // 1. 服务发现中获取服务生产者的实例
//        List<ServiceInstance> instances = discoveryClient.getInstances(hostName);
//
//        // 2. 获取到具体实例 服务生产者实例
//        ServiceInstance serviceInstance = instances.get(0);
        String rest = restTemplate.getForObject(host+url, String.class);
//        String rest = restTemplate.getForObject("http://localhost:8001" + url, String.class);
        System.out.println(rest);
        return rest;
    }
}
