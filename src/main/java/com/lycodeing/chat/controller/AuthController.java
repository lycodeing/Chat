package com.lycodeing.chat.controller;

import com.lycodeing.chat.common.Result;
import com.lycodeing.chat.dto.request.LoginRequest;
import com.lycodeing.chat.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaotianyu
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    @RequestMapping("/login")
    public Result login(@RequestBody @Validated LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return Result.success(token);
    }

    /**
     * 退出
     */
    @RequestMapping("/logout")
    public Result logout() {
        //TODO 移除该token的有效期
        return Result.success("退出成功");
    }

    @RequestMapping("/refreshToken")
    public Result refreshToken() {
        //TODO 刷新token
        return Result.success("刷新成功");
    }

    /**
     * 微信登录
     */
    @RequestMapping("/wxLogin")
    public Result wxLogin() {
        // 创建token
        return Result.success("success");
    }
}
