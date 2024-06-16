package com.lycodeing.chat.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 已认证的用户
 *
 * @author xiaotianyu
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;

    private String username;

}
