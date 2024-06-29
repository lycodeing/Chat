package com.lycodeing.chat.dto;

import com.lycodeing.chat.enums.MsgTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author xiaotianyu
 */
@Data
@Builder
public class PrivateMsgDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    private String receiverId;


    /**
     * 消息Id
     */
    private String messageId;
}