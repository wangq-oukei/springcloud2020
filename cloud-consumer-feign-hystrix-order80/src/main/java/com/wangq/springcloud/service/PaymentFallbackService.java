package com.wangq.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author songdan
 * 统一的服务降级
 * @Created 2021-01-02 15:41
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentFallbackService.paymentInfo_OK，8001已经宕机了";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "PaymentFallbackService.paymentInfo_timeout";
    }
}
