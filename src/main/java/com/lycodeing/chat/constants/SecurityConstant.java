package com.lycodeing.chat.constants;

/**
 * @author xiaotianyu
 */
public final class SecurityConstant {

    /**
     * 认证用户id key
     */
    public static final String AUTH_USER_ID_KEY = "auth_user_id";

    /**
     * 认证用户信息key
     */
    public static final String AUTH_USER_KEY = "auth_user";
    /**
     * 认证请求头key
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    public static final String BEARER_TYPE = "Bearer ";

    private SecurityConstant() {
        // 私有构造器防止实例化
    }
}
