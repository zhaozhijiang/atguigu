package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhijiang.zhao
 * @date 2023/2/6 22:23
 * @description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsulMain8000 {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsulMain8000.class, args);
    }
}
