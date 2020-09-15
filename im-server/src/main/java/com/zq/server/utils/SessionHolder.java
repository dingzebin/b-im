package com.zq.server.utils;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dzeb
 * @version 1.0
 * @Description 存储会话
 * @createTime 2020/9/11 14:44
 */
public class SessionHolder {

    private static final Map<String, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>(16);
    private static final Map<String, String> SESSION_MAP = new ConcurrentHashMap<>(16);

    public static NioSocketChannel get(String id) {
        return CHANNEL_MAP.get(id);
    }

    public static void remove(NioSocketChannel nioSocketChannel) {
        CHANNEL_MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> CHANNEL_MAP.remove(entry.getKey()));
    }

}
