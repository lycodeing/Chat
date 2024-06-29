package com.lycodeing.chat.Listener;

import com.lycodeing.chat.handler.NettyAuthHandler;
import com.lycodeing.chat.utils.RedisUtil;
import com.lycodeing.netty.context.NettyServiceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class ShutdownEventListener implements ApplicationListener<ContextClosedEvent> {

    private final RedisUtil redisUtil;

    public ShutdownEventListener(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        //清理redis的连接
        Set<String> keys = NettyServiceContext.getChannelMap().keySet();
        for (String key : keys) {
            redisUtil.delete(NettyAuthHandler.CONNECT_STATUS_KEY + key);
        }
        log.info("清理redis的连接");
    }
}
