package com.zq.server.coder;

import com.google.gson.Gson;
import com.zq.common.utils.Result;
import com.zq.server.model.Reponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * @author dzeb
 * @version 1.0
 * @Description 出站编码
 * @createTime 2020/9/11 11:09
 */
public class WebSocketMessageEncoder extends MessageToMessageEncoder<Reponse>  {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Reponse data, List<Object> list) throws Exception {
        Gson gson = new Gson();
        list.add(new TextWebSocketFrame(gson.toJson(data)));
    }
}
