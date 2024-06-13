package com.lycodeing.chat.config;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * netty服务上下文
 *
 * @author xiaotianyu
 */
public class NettyServiceContext {

    private static final String USER_ID_KEY = "userId";
    private static final Map<String, Channel> USER_TO_CHANNEL_MAP = new ConcurrentHashMap<>();


    /**
     * 添加连接
     *
     * @param userId  用户ID
     * @param channel 连接
     */
    public static void addChannel(String userId, Channel channel) {
        channel.attr(AttributeKey.valueOf(USER_ID_KEY)).set(userId);
        USER_TO_CHANNEL_MAP.put(userId, channel);
    }

    /**
     * 移除连接
     *
     * @param userId 用户ID
     */
    public static void removeChannel(String userId) {
        USER_TO_CHANNEL_MAP.remove(userId);
    }

    /**
     * 移除连接
     *
     * @param channel 连接
     */
    public static void removeChannel(Channel channel) {
        String userId = (String) channel.attr(AttributeKey.valueOf(USER_ID_KEY)).get();
        if (userId != null) {
            USER_TO_CHANNEL_MAP.remove(userId);
        }
    }

    /**
     * 获取连接
     *
     * @param userId 用户ID
     * @return Channel
     */
    public static Channel getChannel(String userId) {
        return USER_TO_CHANNEL_MAP.get(userId);
    }

    /**
     * 获取用户ID
     *
     * @param channel 连接
     * @return userId
     */
    public static String getUserId(Channel channel) {
        return (String) channel.attr(AttributeKey.valueOf(USER_ID_KEY)).get();
    }

    /**
     * 发送消息给指定用户
     *
     * @param userId  用户ID
     * @param message 消息内容 json
     */
    public static void sendMessage(String userId, String message) {
        Channel channel = getChannel(userId);
        if (channel != null) {
            channel.writeAndFlush(message);
        }
    }

    /**
     * 发送消息给所有用户
     *
     * @param message 消息内容 json
     */
    public static void sendAllMessage(String message) {
        USER_TO_CHANNEL_MAP.values().forEach(channel -> channel.writeAndFlush(message));
    }

}
