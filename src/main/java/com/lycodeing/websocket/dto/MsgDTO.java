package com.lycodeing.websocket.dto;

import com.lycodeing.websocket.enums.MsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaotianyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgDTO {
    /**
     * 消息内容
     */
    private String msg;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 发送人
     */
    private Sender sender;
    /**
     * 接收人
     */
    private Receiver receiver;

    /**
     * 发送时间
     */
    private long sendTime;


    /**
     * 发送人
     */
    @Data
    @Builder
    public static class Sender {
        /**
         *
         */
        private String userId;

        private String nickName;

        private String avatar;
    }

    /**
     * 接收人
     */
    @Data
    @Builder
    public static class Receiver {
        private String userId;

        private String nickName;

        private String avatar;
    }
}


