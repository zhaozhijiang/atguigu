package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.MessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/16 17:21
 * @Description:
 */
@RestController
public class SendMessageController {

    @Autowired
    private MessageProvider messageProvider;

    @GetMapping(value = "/sendMessage")
    public String sendMessage() {
        return messageProvider.send();
    }
}

