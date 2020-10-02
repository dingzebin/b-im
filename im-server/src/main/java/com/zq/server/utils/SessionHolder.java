package com.zq.server.utils;

import com.zq.common.constant.GlobalConstant;
import com.zq.common.utils.RedisUtil;
import com.zq.common.utils.SpringContextHolder;
import com.zq.common.utils.TokenUtil;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dzeb
 * @version 1.0
 * @Description 存储会话
 * @createTime 2020/9/11 14:44
 */
@Slf4j
public class SessionHolder {

    private static final Map<String, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>(16);

    public static NioSocketChannel get(String id) {
        return CHANNEL_MAP.get(id);
    }

    public static void remove(NioSocketChannel nioSocketChannel) {
        RedisUtil redisUtil = SpringContextHolder.getBean(RedisUtil.class);
        CHANNEL_MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel)
                .forEach(entry -> {
                    try {
                        redisUtil.del(GlobalConstant.USER_SERVER_PREFIX + entry, entry.getKey());
                    } catch (Exception e) {
                        log.error("删除用户服务器映射失败 account[{}]", entry.getKey(), e);
                    }
                    try {
                        TokenUtil.removeUser(entry.getKey());
                    } catch (Exception e) {
                        log.error("删除用户token信息失败 account[{}]", entry.getKey(), e);
                    }
                    CHANNEL_MAP.remove(entry.getKey());
                });
    }

    public static void put(String id, NioSocketChannel nioSocketChannel) {
        CHANNEL_MAP.put(id, nioSocketChannel);
    }
}
