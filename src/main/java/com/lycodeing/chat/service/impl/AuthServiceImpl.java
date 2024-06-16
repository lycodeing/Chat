package com.lycodeing.chat.service.impl;

import com.lycodeing.chat.domain.User;
import com.lycodeing.chat.security.AuthenticatedUser;
import com.lycodeing.chat.security.TokenService;
import com.lycodeing.chat.service.AuthService;
import com.lycodeing.chat.service.UserService;
import com.lycodeing.chat.utils.AssertUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * @author xiaotianyu
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final TokenService tokenService;

    public AuthServiceImpl(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }


    @Override
    public String login(String username, String password) {
        User user = userService.getUserByUsername(username);
        AssertUtils.notNull(user, "用户名或密码错误");
        AssertUtils.isTrue(DigestUtils.md5Hex(password).equals(user.getPassword()), "用户名或密码错误");
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
                .userId(user.getId())
                .username(user.getUserName())
                .build();
        return tokenService.generateToken(authenticatedUser);
    }
}
