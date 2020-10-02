package com.zq.common.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author dzeb
 * @version 1.0
 * @Description zookeeper configuration
 * @createTime 2020/9/23 22:29
 */
@Configuration
@Data
public class ZookeeperConfig {

    @Value("${im.zk.address}")
    private String address;

    @Value("${im.zk.timeout}")
    private int timeout;

    @Value("${im.zk.root:/im-root}")
    private String root;
}
