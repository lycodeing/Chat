package com.lycodeing.chat.handler;

import com.lycodeing.chat.config.NettyServiceContext;
import com.lycodeing.chat.exceptions.TokenValidationException;
import com.lycodeing.chat.utils.JwtTokenUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * websocket连接认证处理
 *
 * @author xiaotianyu
 */
@Slf4j
public class NettyAuthHandler extends ChannelInboundHandlerAdapter {
    /**
     * 令牌key
     */
    private static final String AUTHORIZATION_KEY = "Authorization";


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest request) {
            String authorization = request.headers().get(AUTHORIZATION_KEY);
            if (authorization == null) {
                log.info("authorization is null, channel close");
                ctx.close();
            }
            String userId = validateJwtToken(authorization);
            NettyServiceContext.addChannel(userId, ctx.channel());
            super.channelRead(ctx, msg);
        } else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
        NettyServiceContext.removeChannel(ctx.channel());
        log.info("channel inactive, channel close");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught: " + cause.getMessage(), cause);
        ctx.close();
        NettyServiceContext.removeChannel(ctx.channel());
    }


    public String validateJwtToken(String authorization) {
        // 增加对authorization的初步校验
        if (authorization == null || authorization.trim().isEmpty()) {
            throw new TokenValidationException("Authorization header is missing or empty.");
        }
        if (!authorization.startsWith("Bearer ")) {
            throw new TokenValidationException("Authorization header does not start with 'Bearer '.");
        }

        // 从authorization中提取token，这里不需要额外的空检查，因为上面的校验已经确保了authorization的有效性
        String token = authorization.replace("Bearer ", "").trim();

        // 使用jwtTokenUtil验证token的合法性
        if (!JwtTokenUtil.validateToken(token)) {
            throw new TokenValidationException("Token validation failed.");
        }
        return JwtTokenUtil.getUserIdFromToken(token);
    }
}
