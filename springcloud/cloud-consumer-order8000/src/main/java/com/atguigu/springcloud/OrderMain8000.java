package com.atguigu.springcloud;

import com.atguigu.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author zhijiang.zhao
 * @date 2023/2/5 22:21
 * @description: 这里的 CLOUD-PAYMENT-SERVICE 如果是大写就是我们定义的随机访问规则；
 * 如果是小写 cloud-payment-service 则我们定义的不生效，它还是轮询访问
 * 如果是小写，但是把 MySelfRule 放在 @ComponentScan 可以扫描的包下时则又可以随机访问了
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
public class OrderMain8000 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain8000.class, args);
    }
}
