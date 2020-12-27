package com.wangq.springcloud.controller;

import com.wangq.springcloud.entities.CommonResult;
import com.wangq.springcloud.entities.Payment;
import com.wangq.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("payment/create")
    public CommonResult create(@RequestBody Payment payment)
    {
        int res = paymentService.create(payment);
        log.info("插入结果" + res);

        if(res > 0) {
            return new CommonResult(200, "插入成功,serverPort:" + serverPort, res);
        } else {
            return new CommonResult(444, "插入失败", null);
        }
    }

    @GetMapping("payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id)
    {
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询结果" + payment);

        if(payment != null) {
            return new CommonResult(200, "查询成功,serverPort:" + serverPort, payment);
        } else {
            return new CommonResult(444, "没有记录", payment);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery()
    {
        List<String> services = discoveryClient.getServices();
        services.forEach(item -> log.info("*******element:" + item));

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        instances.forEach(is ->log.info(is.getServiceId() + "\t" + is.getHost() + "\t" + is.getPort() + "\t" + is.getUri()));

        return this.discoveryClient;
    }

    @GetMapping("/payment/lb")
    public String getPaymentLb()
    {
        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String getPaymentFeignTimeout()
    {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

}
