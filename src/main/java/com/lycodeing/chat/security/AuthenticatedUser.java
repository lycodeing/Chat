package com.lycodeing.chat.security;

import lombok.Builder;
import lombok.Getter;

/**
 * 已认证的用户
 *
 * @author xiaotianyu
 */
@Builder
@Getter
public class AuthenticatedUser {

    private String userId;

    private String username;

}
