package com.lycodeing.chat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义消息接收
 *
 * @author xiaotianyu
 */
@Slf4j
public class NettyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        log.info("receive message:{}", textWebSocketFrame.text());
        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("server receive message: " + textWebSocketFrame.text()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel active:{}", ctx.channel().id().asLongText());
        // 这里可以获取处于离线状态下的消息然后一次性返回给当前用户


    }
}
