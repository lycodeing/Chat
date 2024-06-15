package com.lycodeing.chat.utils;

import com.lycodeing.chat.security.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaotianyu
 */
@Component
@Slf4j
public class JwtTokenUtil {
    private static final String SECRET_KEY = "websocket-lycodeing-secret";

    /**
     * TODO 等待优化 引入redis控制状态
     * TODO 建议 redis储存 AuthenticatedUser
     * TODO token中记录userId
     *
     * @param username
     * @param userId
     * @return
     */
    public static String generateToken(String username, String userId) {
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("username", username);
        claims.put("userId", userId);
        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24小时有效期
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("username", String.class);
    }

    public static AuthenticatedUser getAuthenticatedUserFromToken(String token) {
        Claims claims = getClaims(token);
        String s = claims.get("userId", String.class);
        // TODO 读取redis中的AuthenticatedUser进行返回
        return AuthenticatedUser.builder()
                .username(claims.get("username", String.class))
                .userId(claims.get("userId", String.class))
                .build();
    }


    public static String getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("userId", String.class);
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("validateToken error",e);
            return false;
        }
    }
}
