package com.lycodeing.websocket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型枚举
 *
 * @author xiaotianyu
 */

@AllArgsConstructor
@Getter
public enum MsgTypeEnum {

    /**
     * 普通消息
     */
    NORMAL(1),

    /**
     * 红包消息
     */
    RED_PACKET(2),

    /**
     * 通知消息
     */
    NOTICE(3),

    /**
     * 公告消息
     */
    ANNOUNCE(4);

    private final int type;


    public static MsgTypeEnum getMsgType(int type) {
        for (MsgTypeEnum msgType : MsgTypeEnum.values()) {
            if (msgType.getType() == type) {
                return msgType;
            }
        }
        return null;
    }
}
