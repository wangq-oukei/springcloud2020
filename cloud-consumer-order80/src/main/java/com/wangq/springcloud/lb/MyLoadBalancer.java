package com.wangq.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author songdan
 * @Description
 * @create 2020-12-21 11:32
 */
public interface MyLoadBalancer {

    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
