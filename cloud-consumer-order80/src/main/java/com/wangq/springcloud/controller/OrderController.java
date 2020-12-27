package com.wangq.springcloud.controller;

import com.wangq.springcloud.entities.CommonResult;
import com.wangq.springcloud.entities.Payment;
import com.wangq.springcloud.lb.MyLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //public static final String URL = "http://localhost:8001";
    public static final String URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private MyLoadBalancer loadBalancer;

    @GetMapping("/consumer/payment/create")
    public CommonResult create(Payment payment)
    {
        return restTemplate.postForObject(
                URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id)
    {
        return restTemplate.getForObject(URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/createForEntity")
    public CommonResult createForEntity(Payment payment)
    {
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(
                URL + "/payment/create", payment, CommonResult.class);

        boolean successful = entity.getStatusCode().is2xxSuccessful();
        if (successful) {
            return entity.getBody();
        }

        return new CommonResult<>(444,"createForEntity操作失败");
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getForEntity(@PathVariable("id") Long id)
    {
        ResponseEntity<CommonResult> entity =
                restTemplate.getForEntity(URL + "/payment/get/" + id, CommonResult.class);

        boolean successful = entity.getStatusCode().is2xxSuccessful();
        if (successful) {
            return entity.getBody();
        }

        return new CommonResult<>(444,"getForEntity取得失败");
    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB()
    {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if(instances == null || instances.size() <= 0)
        {
            return null;
        }

        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }


}
