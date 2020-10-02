package com.zq.route.manage;

import com.google.common.collect.Lists;
import com.zq.common.configuration.ZookeeperConfig;
import com.zq.common.constant.GlobalConstant;
import com.zq.common.exception.BizException;
import com.zq.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dzeb
 * @version 1.0
 * @Description
 * @createTime 2020/9/23 23:27
 */
@Slf4j
@Component
public class ServerManage {
    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ZookeeperConfig zkConfig;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 缓存server
     */
    private Map<String, ServerInfo> serverCache = new ConcurrentHashMap<>(16);

    /**
     * start zookeeper listener
     */
    @PostConstruct
    public void startZkListener() {
        zkClient.subscribeChildChanges(zkConfig.getRoot(), new IZkChildListener() {
            @Override
            public void handleChildChange(String root, List<String> childrens) throws Exception {
                log.info("update local cache root = [{}], childrens = [{}]", root, childrens);
                updateCache(childrens);
            }
        });
    }

    public ServerInfo getServer(String account) {
        List<ServerInfo> servers = this.getServers();
        if (servers.isEmpty()) {
            throw new BizException("have no valid server");
        }
        Random random = new Random();
        ServerInfo serverInfo = null;
        while (true) {
            if (servers.size() == 0) {
                throw  new BizException("have no valid server");
            }
            serverInfo = servers.get(random.nextInt(servers.size()));
            if (this.checkServerAvailable(serverInfo.getIp(), serverInfo.getWs(), 1000)) {
                redisUtil.set(GlobalConstant.USER_SERVER_PREFIX + account, serverInfo, -1);
                break;
            }
            serverInfo = null;
        }

        return serverInfo;
    }

    /**
     * check ip and port
     *
     * @param address
     * @param port
     * @param timeout
     * @return True if connection successful
     */
    public boolean checkServerAvailable(String address, int port, int timeout) {
        Socket socket = new Socket() ;
        try {
            socket.connect(new InetSocketAddress(address, port), timeout);
            return true;
        } catch (IOException exception) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                return false ;
            }
        }
    }

    public void updateCache(List<String> paths) {
        serverCache.clear();
        for (String path : paths) {
            this.serverCache.put(path, ServerInfo.parse(path));
        }
    }

    private List<ServerInfo> getServers() {
        if (serverCache.size() == 0) {
            List<String> childrens = zkClient.getChildren(zkConfig.getRoot());
            this.updateCache(childrens);
        }
        return Lists.newArrayList(serverCache.values());
    }

    public ServerInfo getServerByAccount(String account) {
        return (ServerInfo) redisUtil.get(GlobalConstant.USER_SERVER_PREFIX + account);
    }

}
