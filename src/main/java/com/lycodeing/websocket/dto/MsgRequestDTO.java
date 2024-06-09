package com.lycodeing.websocket.dto;

import lombok.Data;

/**
 * @author xiaotianyu
 */
@Data
public class MsgRequestDTO {
    /**
     * 消息
     */
    private String msg;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 接收者
     */
    private String receiver;

    /**
     * 消息类型
     */
    private Integer msgType;
}
