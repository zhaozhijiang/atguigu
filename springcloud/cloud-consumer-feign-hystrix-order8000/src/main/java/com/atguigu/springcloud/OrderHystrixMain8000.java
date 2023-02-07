package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/7 13:48
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class OrderHystrixMain8000 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain8000.class, args);
    }
}
