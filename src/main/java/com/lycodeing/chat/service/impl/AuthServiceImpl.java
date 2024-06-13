package com.lycodeing.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lycodeing.chat.domain.User;
import com.lycodeing.chat.service.AuthService;
import com.lycodeing.chat.service.UserService;
import com.lycodeing.chat.utils.AssertUtils;
import com.lycodeing.chat.utils.JwtTokenUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author xiaotianyu
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String login(String username, String password) {
        User user = userService.getUserByUsername(username);
        AssertUtils.notNull(user, "用户名或密码错误");
        AssertUtils.isTrue(DigestUtils.md5Hex(password).equals(user.getPassword()), "用户名或密码错误");
        return JwtTokenUtil.generateToken(username, user.getId());
    }
}
