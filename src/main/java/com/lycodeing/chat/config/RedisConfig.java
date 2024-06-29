package com.lycodeing.chat.config;

import com.lycodeing.chat.Listener.RedisMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author xiaotianyu
 */
@Configuration
public class RedisConfig {

    public static final String CHANNEL_NAME = "chat";

    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                RedisMessageListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 订阅名为my-channel的频道
        ChannelTopic topic = new ChannelTopic(CHANNEL_NAME);
        container.addMessageListener(listener, topic);
        return container;
    }
}
