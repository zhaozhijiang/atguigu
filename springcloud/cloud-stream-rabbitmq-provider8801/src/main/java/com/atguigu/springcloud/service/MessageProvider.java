package com.atguigu.springcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @EnableBinding(Source.class) :可以理解为是一个消息的发送管道的定义
 * @author: zhijiang.zhao
 * @date: 2023/2/16 17:17
 * @Description:
 */
@Service
@EnableBinding(Source.class)
@Slf4j
public class MessageProvider {
    /**
     * 消息的发送管道
     */
    @Autowired
    private MessageChannel output;

    public String send() {
        String serial = UUID.randomUUID().toString();
        // 创建并发送消息
        this.output.send(MessageBuilder.withPayload(serial).build());
        log.info("serial:{}", serial);

        return serial;
    }
}
