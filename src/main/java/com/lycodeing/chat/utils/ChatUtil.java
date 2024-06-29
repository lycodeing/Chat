package com.lycodeing.chat.utils;

import com.lycodeing.chat.config.RedisConfig;
import com.lycodeing.netty.context.NettyServiceContext;
import com.lycodeing.netty.utils.MessageUtil;
import io.netty.channel.Channel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ChatUtil {


    private final RedisTemplate redisTemplate;

    public ChatUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void sendMsg(String key, Object msg) {
        Channel channel = NettyServiceContext.getChannel(key);
        if (channel != null) {
            MessageUtil.sendMessage(key, msg);
        } else {
            redisTemplate.convertAndSend(RedisConfig.CHANNEL_NAME, msg);
        }
    }
}
