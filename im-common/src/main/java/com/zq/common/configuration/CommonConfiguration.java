package com.zq.common.configuration;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author dzeb
 * @version 1.0
 * @Description TODO
 * @createTime 2020/9/17 12:00 上午
 */
@Configuration
public class CommonConfiguration {
    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return factory;
    }

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(zookeeperConfig.getAddress(), zookeeperConfig.getTimeout());
    }

}
