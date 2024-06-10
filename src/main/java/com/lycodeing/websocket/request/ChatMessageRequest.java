package com.lycodeing.websocket.request;

import com.lycodeing.websocket.enums.MsgTypeEnum;
import lombok.Data;

/**
 * @author xiaotianyu
 */
@Data
public class ChatMessageRequest {
    private String msg;

    private String sender;

    private String receiver;

    /**
     * 消息类型
     * {@link MsgTypeEnum}
     */
    private Integer msgType;
}
