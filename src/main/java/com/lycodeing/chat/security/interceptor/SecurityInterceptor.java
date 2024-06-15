package com.lycodeing.chat.security.interceptor;

import com.lycodeing.chat.common.Result;
import com.lycodeing.chat.constants.SecurityConstant;
import com.lycodeing.chat.security.AuthenticatedUser;
import com.lycodeing.chat.security.AuthenticatedUserContext;
import com.lycodeing.chat.security.properties.SecurityProperties;
import com.lycodeing.chat.utils.GsonUtil;
import com.lycodeing.chat.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * 安全拦截器
 *
 * @author xiaotianyu
 */
@Component
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    private final SecurityProperties securityProperties;

    @Autowired
    public SecurityInterceptor(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        if (isAllowed(request)) {
            return true;
        }
        String authorization = request.getHeader(SecurityConstant.AUTH_HEADER);
        // 检查Authorization头是否存在且格式正确
        if (StringUtils.isBlank(authorization) || !authorization.startsWith(SecurityConstant.BEARER_TYPE)) {
            log.warn("Invalid or missing Authorization header.");
            handleUnauthorized(response, "无效的认证信息");
            return false;
        }

        String token = authorization.substring(SecurityConstant.BEARER_TYPE.length());

        try {
            // 验证Token有效性
            if (!JwtTokenUtil.validateToken(token)) {
                log.warn("Token validation failed.");
                handleUnauthorized(response, "Token验证失败");
                return false;
            }

            // 提取并设置用户信息到上下文
            AuthenticatedUser authenticatedUser = JwtTokenUtil.getAuthenticatedUserFromToken(token);
            AuthenticatedUserContext.set(authenticatedUser);
            return true;
        } catch (SecurityException se) {
            log.error("Security issue with token validation.", se);
            handleUnauthorized(response, "安全验证错误");
            return false;
        } catch (IllegalArgumentException iae) {
            log.error("Illegal argument exception during token processing.", iae);
            handleUnauthorized(response, "非法参数错误");
            return false;
        } catch (Exception e) {
            log.error("Unexpected error during token handling.", e);
            handleInternalServerError(response, "服务器内部错误");
            return false;
        }
    }


    /**
     * 判断是否放行请求
     */
    private boolean isAllowed(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        return securityProperties.getWhiteList().stream()
                .anyMatch(requestPath::startsWith);
    }


    private void handleUnauthorized(HttpServletResponse response, String errorMsg) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(GsonUtil.toJson(Result.error(errorMsg)));
    }

    private void handleInternalServerError(HttpServletResponse response, String errorMsg) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(GsonUtil.toJson(Result.error(errorMsg)));
    }
}
