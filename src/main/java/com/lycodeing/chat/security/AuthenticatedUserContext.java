package com.lycodeing.chat.security;

/**
 * 已登录的用户的上下文信息
 *
 * @author xiaotianyu
 */
public class AuthenticatedUserContext {
    private static final ThreadLocal<AuthenticatedUser> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(AuthenticatedUser authenticatedUser) {
        THREAD_LOCAL.set(authenticatedUser);
    }

    public static AuthenticatedUser get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }


}
