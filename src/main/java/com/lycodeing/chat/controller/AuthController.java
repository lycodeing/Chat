package com.lycodeing.chat.controller;

import com.lycodeing.chat.common.Result;
import com.lycodeing.chat.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/login")
    public Result login(@RequestBody Map<String ,String> map){
        String token = jwtTokenUtil.generateToken(map.get("username"));
        return Result.success(token);
    }
}
