package com.zq.server.handler;

import com.zq.common.utils.Result;
import com.zq.server.constant.CommandType;
import com.zq.server.model.Reponse;
import com.zq.server.model.Request;
import com.zq.server.utils.SessionHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.coyote.Response;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author dzeb
 * @version 1.0
 * @Description 事件处理器
 * @createTime 2020/9/11 11:02
 */
public class FinalChanelEventHandler extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        // 登录验证
        if (Objects.equals(request.getCode(), CommandType.LOGIN)) {
            String account = request.getBody();
            if (StringUtils.isEmpty(account)) {
                ctx.channel().writeAndFlush(Reponse.newInstance(Reponse.CONNECT_FAILED, "登录验证失败", null));
            } else {
                NioSocketChannel channel = SessionHolder.get(account);
                if (channel != null) {
                   channel.writeAndFlush(Reponse.newInstance(Reponse.ERR, "账号在其他地方登录", null));
                   channel.close();
                }
                SessionHolder.put(account, (NioSocketChannel) ctx.channel());
                ctx.channel().writeAndFlush(Reponse.newInstance(Reponse.CONNECTED, "账号验证成功", null));
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive.....");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionHolder.remove((NioSocketChannel) ctx.channel());
    }
}
