package com.wangq.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author songdan
 * @Description
 * @create 2020-12-20 21:00
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule()
    {
        return new RandomRule(); // 随机
    }
}
