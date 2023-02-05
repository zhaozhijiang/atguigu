package com.atguigu.springcloud.service;

import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhijiang.zhao
 * @date 2023/2/5 19:10
 * @description: TODO
 */
@Service
@Slf4j
public class PaymentService {
    @Autowired
    private PaymentDao paymentDao;

    /**
     * 创建一个 payment
     *
     * @param payment
     * @return
     */
    public CommonResult create(Payment payment) {
        int result = paymentDao.create(payment);
        log.info("*****插入操作返回结果:{}", result);
        if (result > 0) {
            return new CommonResult(200, "插入数据库成功", result);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    /**
     * 根据 id 查询 payment
     *
     * @param id
     * @return
     */
    public CommonResult getPaymentById(Long id) {
        Payment payment = paymentDao.getPaymentById(id);
        log.info("*****查询结果:{}", payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功", payment);
        } else {
            return new CommonResult(444, "没有对应记录,查询ID: " + id, null);
        }
    }
}
