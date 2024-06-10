package com.lycodeing.websocket.dto;

import com.lycodeing.websocket.enums.MsgTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceMsgDTO {
    /**
     * 消息内容
     */
    private String msg;

    /**
     * 消息类型
     * {@link MsgTypeEnum}
     */
    private Integer msgType;

    /**
     * 发送时间
     */
    private long sendTime;

    /**
     * 发送人Id
     */
    private String senderId;

    /**
     * 接收人Id
     */
    private String serviceId;
}
