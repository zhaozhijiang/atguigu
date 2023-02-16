package com.atguigu.springcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/16 17:36
 * @Description:
 */
@Component
@EnableBinding(Sink.class)
@Slf4j
public class ReceiveMessageListener {

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        log.info("消费者1号，------->接收到的消息：{}，port:{}", message.getPayload(), serverPort);
    }
}

