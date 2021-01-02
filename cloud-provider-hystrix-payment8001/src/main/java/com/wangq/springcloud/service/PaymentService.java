package com.wangq.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author songdan
 * @Description
 * @create 2020-12-27 19:37
 */
@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName()
                + "paymentInfo_OK,id:  " + id +"\t"+"O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",
        commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")}
    )
    public String paymentInfo_timeout(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName()
                + "paymentInfo_timeout,id = " + id + "\t"+"O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",
            commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")}
    )
    public String paymentInfo_exception(Integer id) {

        int age = 10 / 0;
        return "线程池：" + Thread.currentThread().getName()
                + "paymentInfo_exception,id = " + id + "\t"+"O(∩_∩)O哈哈~";
    }

    /**
     * 服务降级
     * @param id
     * @return
     */
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName()
                + "系统繁忙稍后再试" + "\t"+"😭~";
    }
}
