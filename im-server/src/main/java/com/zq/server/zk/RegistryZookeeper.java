package com.zq.server.zk;

import com.zq.common.configuration.ZookeeperConfig;
import com.zq.common.utils.SpringContextHolder;
import com.zq.server.config.IMConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author dzeb
 * @version 1.0
 * @Description register to zookeeper
 * @createTime 2020/9/23 22:26
 */
@Slf4j
public class RegistryZookeeper implements Runnable {
    private String ip;

    private ZookeeperConfig zkConfig;

    private IMConfiguration imConfig;

    private ZkClient zkClient;

    public RegistryZookeeper(String ip) {
        zkConfig = SpringContextHolder.getBean(ZookeeperConfig.class);
        imConfig = SpringContextHolder.getBean(IMConfiguration.class);
        zkClient = SpringContextHolder.getBean(ZkClient.class);
        this.ip = ip;
    }

    @Override
    public void run() {
        this.createRoot();
        this.createNode();
    }

    private void createRoot() {
        boolean exists = zkClient.exists(zkConfig.getRoot());
        if (exists) {
            return;
        }
        zkClient.createPersistent(zkConfig.getRoot());
    }

    private void createNode() {
        String path = zkConfig.getRoot() + "/serverinfo-" + this.ip + ":" + imConfig.getHttpPort() + ":" + imConfig.getWsPort();
        zkClient.createEphemeral(path);
        log.info("Regiter to zookeeper success, the path is [{}]", path);
    }
}
