package com.lycodeing.chat.dto.request;

import com.lycodeing.chat.enums.MsgTypeEnum;
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
