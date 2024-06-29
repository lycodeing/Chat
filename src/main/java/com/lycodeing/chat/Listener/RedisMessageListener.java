package com.lycodeing.chat.Listener;

import com.lycodeing.chat.dto.PrivateMsgDTO;
import com.lycodeing.chat.utils.GsonUtil;
import com.lycodeing.netty.context.NettyServiceContext;
import com.lycodeing.netty.utils.MessageUtil;
import io.netty.channel.Channel;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author xiaotianyu
 */
@Component
public class RedisMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msgBody = new String(message.getBody(), StandardCharsets.UTF_8);
        PrivateMsgDTO privateMsgDTO = GsonUtil.fromJson(msgBody, PrivateMsgDTO.class);
        Channel channel = NettyServiceContext.getChannel(privateMsgDTO.getReceiverId());
        if (channel != null) {
            MessageUtil.sendMessage(privateMsgDTO.getReceiverId(), privateMsgDTO);
        }
    }
}
