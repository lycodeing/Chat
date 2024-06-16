package com.lycodeing.chat.controller;

import com.lycodeing.chat.common.Result;
import com.lycodeing.chat.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块接口
 *
 * @author xiaotianyu
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getFriendUserList")
    public Result getFriendUserList(String username) {
        // TODO 封装下返回UserResponse
        return Result.success(userService.getFriendUserList(username));
    }
}
