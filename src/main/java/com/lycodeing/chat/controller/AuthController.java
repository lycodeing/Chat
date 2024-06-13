package com.lycodeing.chat.controller;

import com.lycodeing.chat.common.Result;
import com.lycodeing.chat.request.LoginRequest;
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
}
