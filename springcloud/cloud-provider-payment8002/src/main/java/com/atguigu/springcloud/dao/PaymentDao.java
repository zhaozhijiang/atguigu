package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhijiang.zhao
 * @date 2023/2/5 19:11
 * @description: TODO
 */
@Mapper
public interface PaymentDao {
    /**
     * 创建一个 payment
     *
     * @param payment
     * @return
     */
    int create(Payment payment);

    /**
     * 根据 id 查询 payment
     *
     * @param id
     * @return
     */
    Payment getPaymentById(@Param("id") Long id);
}
