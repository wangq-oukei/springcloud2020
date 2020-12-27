package com.wangq.springcloud.service;

import com.wangq.springcloud.entities.CommonResult;
import com.wangq.springcloud.entities.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author songdan
 * @Description
 * @create 2020-12-21 15:35
 */
@Component
@FeignClient(value = "cloud-payment-service")
public interface PaymentFeignService {

    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> get(@PathVariable("id") Long id);

    @GetMapping("/payment/feign/timeout")
    String getPaymentFeignTimeout();
}
