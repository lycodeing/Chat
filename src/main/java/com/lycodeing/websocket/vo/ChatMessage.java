package com.lycodeing.websocket.vo;

import lombok.Data;

/**
 * @author xiaotianyu
 */
@Data
public class ChatMessage {
    private String msg;

    private String sender;

    private String receiver;
}
