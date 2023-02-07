package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/7 10:08
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients
public class OrderFeignMain8000 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain8000.class, args);
    }
}
