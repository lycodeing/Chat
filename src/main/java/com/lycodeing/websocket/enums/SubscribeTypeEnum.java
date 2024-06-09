package com.lycodeing.websocket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订阅
 * @author xiaotianyu
 */
@AllArgsConstructor
@Getter
public enum SubscribeTypeEnum {
    /**
     * 群聊
     */
    GROUP("/group/%s/chat"),
    /**
     * 私聊
     */
    PRIVATE("/private/%s/chat"),
    /**
     * 系统消息
     */
    SERVICE("/service/%s/chat");


    private final String  url;
}
