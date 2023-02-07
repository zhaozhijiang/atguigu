package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/7 10:09
 * @Description: @FeignClient value 的内容就是 Eureka 中要调用的微服务的名
 */
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {
    /**
     * 根据 id 查询 payment
     *
     * @param id
     * @return
     */
    @GetMapping("/payment/get/{id}")
    CommonResult getPaymentById(@PathVariable("id") Long id);

    /**
     * 模拟超时
     *
     * @return
     */
    @GetMapping(value = "/payment/feign/timeout")
    String paymentFeignTimeOut();
}
