package com.lycodeing.chat.controller;

import com.lycodeing.chat.common.Result;
import com.lycodeing.chat.constants.Constant;
import com.lycodeing.chat.utils.RedisUtil;
import com.lycodeing.netty.context.NettyServiceContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaotianyu
 */
@RestController
public class CloseController {

    private final RedisUtil<Boolean> redisUtil;

    public CloseController(RedisUtil<Boolean> redisUtil) {
        this.redisUtil = redisUtil;
    }

    @PostMapping("/close")
    public Result close() {
        NettyServiceContext.getChannelMap().keySet().forEach(key -> {
            redisUtil.delete(Constant.CONNECT_STATUS_KEY + key);
        });
        return Result.success(null);
    }
}
