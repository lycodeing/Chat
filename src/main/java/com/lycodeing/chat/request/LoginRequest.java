package com.lycodeing.chat.request;

import lombok.Data;

/**
 * @author xiaotianyu
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
