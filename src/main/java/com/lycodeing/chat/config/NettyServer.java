package com.lycodeing.chat.config;

import com.lycodeing.chat.handler.NettyAuthHandler;
import com.lycodeing.chat.handler.NettyHeartBeatHandler;
import com.lycodeing.chat.handler.NettyWebSocketHandler;
import com.lycodeing.chat.security.TokenService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * websocket server
 *
 * @author xiaotianyu
 */
@Slf4j
@Component
public class NettyServer {

    @Value("${netty.port}")
    private Integer port;

    @Value("${netty.maxContentLength}")
    private Integer maxContentLength;

    @Value("${netty.websocketPath}")
    private String websocketPath;

    @Value("${netty.idleTimeout}")
    private Integer idleTimeout;

    private final TokenService tokenService;

    @Autowired
    public NettyServer(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(maxContentLength));
                            pipeline.addLast(new NettyAuthHandler(tokenService));
                            // 心跳检查
                            pipeline.addLast(new IdleStateHandler(0, 0, idleTimeout)); // 读写空闲超时时间为5秒
                            pipeline.addLast(new NettyHeartBeatHandler());
                            pipeline.addLast(new WebSocketServerProtocolHandler(websocketPath));
                            pipeline.addLast(new NettyWebSocketHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, false);

            ChannelFuture future = server.bind(port).sync();
            log.info("netty服务器启动成功，端口：{}", port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("启动netty服务器失败", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @PostConstruct
    public void init() {
        Thread thread = new Thread(this::start);
        thread.setName("netty-server");
        thread.start();
    }
}
