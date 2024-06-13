package com.lycodeing.chat.handler;

import com.lycodeing.chat.config.NettyServiceContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 连接心跳检查
 * 需要客户端端发送心跳包
 *
 * @author xiaotianyu
 */
@Slf4j
public class NettyHeartBeatHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            if (event.state() == IdleState.READER_IDLE) {
                ctx.close();
                NettyServiceContext.removeChannel(ctx.channel());
            }
        }
        super.userEventTriggered(ctx, evt);
    }

}
