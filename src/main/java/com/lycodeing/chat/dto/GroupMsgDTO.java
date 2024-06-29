package com.lycodeing.chat.dto;

import com.lycodeing.chat.enums.MsgTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @author xiaotianyu
 */
@Data
@Builder
public class GroupMsgDTO {

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
     * 发送人
     */
    private Sender sender;

    /**
     * 接收群组ID
     */
    private String groupId;

    private String messageId;

    /**
     * 发送人
     */
    @Data
    @Builder
    public static class Sender {
        /**
         *
         */
        private String senderId;

        private String nickName;

        private String avatar;
    }
}
