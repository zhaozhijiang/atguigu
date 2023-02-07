package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/7 9:45
 * @Description: MySelfRule规则类
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        // 定义为随机
        return new RandomRule();
    }
}
