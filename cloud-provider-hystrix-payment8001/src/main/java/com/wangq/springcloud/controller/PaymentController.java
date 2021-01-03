package com.wangq.springcloud.controller;

import com.wangq.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songdan
 * @Description
 * @create 2020-12-27 19:43
 */
@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String infoOk = paymentService.paymentInfo_OK(id);
        log.info("******" + infoOk);
        return infoOk;
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_timeout(@PathVariable("id") Integer id) {
        String timeout = paymentService.paymentInfo_timeout(id);
        log.info("******" + timeout);
        return timeout;
    }

    @GetMapping("/payment/hystrix/exception/{id}")
    public String paymentInfo_exception(@PathVariable("id") Integer id) {
        String timeout = paymentService.paymentInfo_exception(id);
        log.info("******" + timeout);
        return timeout;
    }

    @GetMapping("/payment/hystrix/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("******" + result);
        return result;
    }

}
