package com.atguigu.springcloud.service.fallback;

import com.atguigu.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/7 14:15
 * @Description:
 */
@Component
public class PaymentFallbackServiceImpl implements PaymentHystrixService {

    @Override
    public String paymentInfoOk(Integer id) {
        return "====PaymentHystrixService fall back paymentInfoOk，o(╥﹏╥)o====";
    }

    @Override
    public String paymentInfoTimeOut(Integer id) {
        return "====PaymentHystrixService fall back paymentInfoTimeOut，o(╥﹏╥)o====";
    }
}
