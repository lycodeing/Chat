package com.lycodeing.chat.security;

import com.lycodeing.chat.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xiaotianyu
 */
@Component
@Slf4j
public class TokenService {
    private static final String SECRET_KEY = "websocket-lycodeing-secret";

    private static final String AUTHENTICATED_USER_KEY = "AuthenticatedUser:";

    /**
     * 过期时间 30分钟
     */
    private static final long EXPIRATION_TIME = 30 * 60 * 1000;


    private final RedisUtil<AuthenticatedUser> redisUtil;

    public TokenService(RedisUtil<AuthenticatedUser> redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 生成token
     *
     * @param authenticatedUser 认证的用户信息
     * @return token
     */
    public String generateToken(AuthenticatedUser authenticatedUser) {
        redisUtil.set(AUTHENTICATED_USER_KEY + authenticatedUser.getUserId(), authenticatedUser, EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(authenticatedUser.getUserId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 24小时有效期
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parserToken(token);
            return true;
        } catch (Exception e) {
            log.error("token解析失败", e);
            return false;
        }
    }

    public void removeToken(String token) {
        Claims claims = parserToken(token);
        String userId = claims.getSubject();
        redisUtil.delete(AUTHENTICATED_USER_KEY + userId);
    }

    public AuthenticatedUser getAuthenticatedUser(String token) {
        Claims claims = parserToken(token);
        String userId = claims.getSubject();
        AuthenticatedUser authenticatedUser = redisUtil.get(AUTHENTICATED_USER_KEY + userId);
        // 更新有效期
        redisUtil.set(AUTHENTICATED_USER_KEY + userId, authenticatedUser, EXPIRATION_TIME);
        return authenticatedUser;
    }

    private Claims parserToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("token解析失败", e);
            throw new RuntimeException("token解析失败");
        }
    }


}
