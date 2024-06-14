package com.lycodeing.chat.dto.response;

import lombok.Data;

/**
 * @author xiaotianyu
 */
@Data
public class UserResponse {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 最后一次发送消息的时间
     */
    private long lastMsgTime;
}
