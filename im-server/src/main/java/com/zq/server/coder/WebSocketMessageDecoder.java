package com.zq.server.coder;

import com.zq.server.model.Request;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dzeb
 * @version 1.0
 * @Description 入站解码
 * @createTime 2020/9/11 11:10
 */
public class WebSocketMessageDecoder extends SimpleChannelInboundHandler<Object> {
    private static ConcurrentHashMap<String, WebSocketServerHandshaker> handShakerMap = new ConcurrentHashMap();

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) msg).text();
            ctx.fireChannelRead(new Gson().fromJson(text, Request.class));
        }
    }
}
