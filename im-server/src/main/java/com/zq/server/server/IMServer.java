package com.zq.server.server;

import com.zq.server.coder.WebSocketMessageDecoder;
import com.zq.server.config.IMConfiguration;
import com.zq.server.handler.FinalChanelEventHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author dzeb
 * @version 1.0
 * @Description create websocket server and app socket server
 * @createTime 2020/9/11 10:46
 */
@Component
@Slf4j
public class IMServer {
    @Autowired
    private IMConfiguration imConfig;

    /** app thread group */
    private EventLoopGroup appBossGroup;
    private EventLoopGroup appWorkerGroup;

    /** websocket thread group */
    private EventLoopGroup wsBossGroup;
    private EventLoopGroup wsWorkerGroup;

    @PostConstruct
    public void start() {
        if (imConfig.getWsPort() != null) {
            this.startWsSocket();
        }
        if (imConfig.getAppPort() != null) {
            this.startAppSocket();
        }
    }

    @PreDestroy
    public void destroy() {
        this.destroy(this.appBossGroup, this.appWorkerGroup);
        this.destroy(this.wsBossGroup, this.wsWorkerGroup);
    }

    public void startWsSocket() {
        log.info("websocket server starting...");
        this.wsBossGroup = new NioEventLoopGroup();
        this.wsWorkerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = this.createServerBootstrap(this.wsBossGroup, this.wsWorkerGroup);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(new ChannelHandler[]{new HttpServerCodec()});
                ch.pipeline().addLast(new ChannelHandler[]{new ChunkedWriteHandler()});
                ch.pipeline().addLast(new ChannelHandler[]{new HttpObjectAggregator(65536)});
                ch.pipeline().addLast(new ChannelHandler[]{new WebSocketServerProtocolHandler("/websocket")});
                ch.pipeline().addLast(new ChannelHandler[]{new WebSocketMessageDecoder()});
                ch.pipeline().addLast(new ChannelHandler[]{new LoggingHandler(LogLevel.INFO)});

                ch.pipeline().addLast(new ChannelHandler[]{new FinalChanelEventHandler()});
            }
        });
        ChannelFuture channelFuture = bootstrap.bind(this.imConfig.getWsPort()).syncUninterruptibly();
        channelFuture.channel().newSucceededFuture().addListener((future) -> {
            String logBanner = "\n\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n*                                                                                   *\n*                                                                                   *\n*                   Websocket Server started on port {}.                         *\n*                                                                                   *\n*                                                                                   *\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";
            log.info(logBanner, this.imConfig.getWsPort());
        });
        channelFuture.channel().closeFuture().addListener((future) -> {
            this.destroy(this.wsBossGroup, this.wsWorkerGroup);
        });
    }

    public void startAppSocket() {
        log.info("app socket starting..........");

        log.info("app socket started, port({})", imConfig.getWsPort());
    }


    private ServerBootstrap createServerBootstrap(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.channel(NioServerSocketChannel.class);
        return bootstrap;
    }

    /**
     * 销毁
     * @param bossGroup
     * @param workerGroup
     */
    private void destroy(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        if (bossGroup != null && !bossGroup.isShuttingDown() && !bossGroup.isShutdown()) {
            try {
                bossGroup.shutdownGracefully();
            } catch (Exception var5) {
            }
        }

        if (workerGroup != null && !workerGroup.isShuttingDown() && !workerGroup.isShutdown()) {
            try {
                workerGroup.shutdownGracefully();
            } catch (Exception var4) {
            }
        }

    }
}
