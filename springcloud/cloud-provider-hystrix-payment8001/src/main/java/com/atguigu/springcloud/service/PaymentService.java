package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/7 13:35
 * @Description: 超时导致服务器变慢(转圈)：超时不再等待
 * 出错(宕机或程序运行出错)：出错要有兜底
 * 解决：
 * 对方服务(8001)超时了，调用者(80)不能一直卡死等待，必须有服务降级
 * 对方服务(8001)宕机了，调用者(80)不能一直卡死等待，必须有服务降级
 * 对方服务(8001)OK，调用者(80)自己出故障或有自我要求（自己的等待时间小于服务提供者）自己处理降级
 * <p>
 * 降级配置：@HystrixCommand
 */
@Service
public class PaymentService {
    /**
     * 正常访问一切 OK
     *
     * @param id
     * @return
     */
    public String paymentInfoOk(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_OK,id: " + id + "\t" + "O(∩_∩)O";
    }

    /**
     * 超时访问，演示降级
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfoTimeOut(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_TimeOut,id: " + id + "\t" + "O(∩_∩)O，耗费3秒";
    }

    /**
     * 超时访问的降级方法
     *
     * @param id
     * @return
     */
    public String paymentInfoTimeOutHandler(Integer id) {
        return "/(ㄒoㄒ)/调用支付接口超时或异常：\t" + "\t当前线程池名字" + Thread.currentThread().getName();
    }
}
