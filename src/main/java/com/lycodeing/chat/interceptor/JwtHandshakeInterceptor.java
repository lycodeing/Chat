package com.lycodeing.chat.interceptor;

import com.lycodeing.chat.exceptions.TokenValidationException;
import com.lycodeing.chat.service.GroupUserService;
import com.lycodeing.chat.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xiaotianyu
 */
@Slf4j
@Component
public class JwtHandshakeInterceptor implements ChannelInterceptor {
    /**
     * 认证Token header_key
     */
    private static final String HEADER_TOKEN_KEY = "Authorization";
    private final JwtTokenUtil jwtTokenUtil;

    private final GroupUserService groupUserService;

    @Autowired
    public JwtHandshakeInterceptor(JwtTokenUtil jwtTokenUtil, GroupUserService groupUserService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.groupUserService = groupUserService;
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // 获取Token信息
        String authorization = accessor.getFirstNativeHeader(HEADER_TOKEN_KEY);
        String username = null;
        if (Objects.nonNull(accessor.getCommand())) {
            switch (accessor.getCommand()) {
                case CONNECT, SEND -> {
                    username = validateJwtToken(authorization);
                    log.info("用户：{},触发事件：{},通道：{}", username, accessor.getCommand(), accessor.getDestination());
                }
                case SUBSCRIBE -> {
                    username = validateJwtToken(authorization);
                    if (!groupUserService.isGroupMember(username, accessor.getDestination())) {
                        throw new RuntimeException("用户不是群成员,禁止订阅");
                    }
                    log.info("用户：{},订阅消息,通道：{}", username, accessor.getDestination());
                }
                case DISCONNECT -> log.info("断开连接");
                default -> {
                    log.info("未知事件：{},通道：{}", accessor.getCommand(), accessor.getDestination());
                }
            }
        }


        return message;
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
        if (!jwtTokenUtil.validateToken(token)) {
            throw new TokenValidationException("Token validation failed.");
        }
        return jwtTokenUtil.getUsernameFromToken(token);
    }
}