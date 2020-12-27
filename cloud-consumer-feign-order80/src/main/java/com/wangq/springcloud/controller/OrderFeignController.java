package com.wangq.springcloud.controller;

import com.wangq.springcloud.entities.CommonResult;
import com.wangq.springcloud.entities.Payment;
import com.wangq.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author songdan
 * @Description
 * @create 2020-12-21 15:42
 */
@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/getFeign/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        CommonResult<Payment> paymentCommonResult = paymentFeignService.get(id);

        return paymentCommonResult;
    }

    @GetMapping("/consumer/payment/feign/timeout")
    public String getPaymentFeignTimeout()
    {
        return paymentFeignService.getPaymentFeignTimeout();
    }

}
