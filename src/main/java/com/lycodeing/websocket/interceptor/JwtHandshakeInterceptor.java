package com.lycodeing.websocket.interceptor;

import com.lycodeing.websocket.exceptions.TokenValidationException;
import com.lycodeing.websocket.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Objects;
import java.util.UUID;

@Slf4j
public class JwtHandshakeInterceptor implements ChannelInterceptor {
    private final JwtTokenUtil jwtTokenUtil;

    public JwtHandshakeInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // 获取Token信息
        String authorization = accessor.getFirstNativeHeader("Authorization");
        String username = null;
        if (Objects.nonNull(accessor.getCommand())) {
            switch (accessor.getCommand()) {
                case CONNECT, SEND -> {
                    username = validateJwtToken(authorization);
                    log.info("用户：{},触发事件：{},通道：{}", username, accessor.getCommand(), accessor.getDestination());
                }
                case SUBSCRIBE -> {
                    username = validateJwtToken(authorization);
                    if (!isGroupMember(username, accessor.getDestination())) {
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


    private boolean isGroupMember(String username,String destination) {
        // 在这里实现验证用户是否是群成员的逻辑
        // 可以从数据库或其他存储中获取群成员信息进行验证
        return true; // 假设所有用户都是群成员，实际应根据具体逻辑来判断
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
