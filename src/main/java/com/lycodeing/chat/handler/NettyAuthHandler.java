package com.lycodeing.chat.handler;

import com.lycodeing.chat.constants.Constant;
import com.lycodeing.chat.exceptions.TokenValidationException;
import com.lycodeing.chat.security.AuthenticatedUser;
import com.lycodeing.chat.security.AuthenticatedUserContext;
import com.lycodeing.chat.security.TokenService;
import com.lycodeing.chat.utils.RedisUtil;
import com.lycodeing.netty.context.NettyServiceContext;
import com.lycodeing.netty.exception.EndPipelineException;
import com.lycodeing.netty.handlers.abs.AbstractAuthHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * websocket连接认证处理
 *
 * @author xiaotianyu
 */
@Slf4j
@Component
public class NettyAuthHandler extends AbstractAuthHandler {


    private final TokenService tokenService;

    private final RedisUtil<Boolean> redisUtil;

    public NettyAuthHandler(TokenService tokenService,
                            RedisUtil<Boolean> redisUtil) {
        this.tokenService = tokenService;
        this.redisUtil = redisUtil;
    }


    @Override
    public void auth(ChannelHandlerContext ctx, FullHttpRequest request) {
        String authorization = getAuthorization(request);
        AuthenticatedUser authenticatedUser = validateJwtToken(authorization);
        if (isConnected(authenticatedUser.getUserId())) {
            throw new EndPipelineException("当前连接已存在，不允许创建多个连接");
        }
        saveConnectStatus(authenticatedUser.getUserId(), ctx);

    }

    @Override
    public void close(ChannelHandlerContext ctx) {
        String userId = NettyServiceContext.getChannelKey(ctx.channel());
        closeConnect(userId);
    }


    public void saveConnectStatus(String userId, ChannelHandlerContext ctx) {
        NettyServiceContext.addChannel(userId, ctx.channel());
        redisUtil.set(Constant.CONNECT_STATUS_KEY + userId, true, 3600L);
    }


    public void closeConnect(String userId) {
        redisUtil.delete(Constant.CONNECT_STATUS_KEY + userId);
        NettyServiceContext.removeChannel(userId);
    }


    /**
     * 判断是否连接
     *
     * @param userId
     * @return
     */
    public boolean isConnected(String userId) {
        Channel channel = NettyServiceContext.getChannel(userId);
        if (channel != null) {
            return true;
        }
        Boolean connectStatus = redisUtil.get(Constant.CONNECT_STATUS_KEY + userId);
        return connectStatus != null && connectStatus;
    }

    /**
     * 读取AUTHORIZATION_KEY
     */
    public String getAuthorization(FullHttpRequest request) {
        String authorization = request.headers().get(Constant.AUTHORIZATION_KEY);
        if (authorization == null) {
            log.info("authorization is null");
            return null;
        }
        return authorization;
    }


    /**
     * 验证JWT令牌
     * 可引入redis控制 并读取redis储存的用户信息
     * 并写入 AuthenticatedUserContext 中
     *
     * @param authorization 令牌
     * @return
     */
    public AuthenticatedUser validateJwtToken(String authorization) {
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
        if (!tokenService.validateToken(token)) {
            throw new TokenValidationException("Token validation failed.");
        }
        AuthenticatedUser authenticatedUser = tokenService.getAuthenticatedUser(token);
        AuthenticatedUserContext.set(authenticatedUser);
        return authenticatedUser;
    }
}
