package com.lycodeing.chat.service;

import com.lycodeing.chat.dto.GroupMsgDTO;
import com.lycodeing.chat.dto.PrivateMsgDTO;
import com.lycodeing.chat.dto.ServiceMsgDTO;

/**
 * @author xiaotianyu
 */
public interface ChatService {

    /**
     * 发送群聊消息
     * @param msg 消息
     */
    void sendGroupMsg(GroupMsgDTO msg);

    /**
     * 发送私聊消息
     * @param msg 消息
     */
    void sendPrivateMsg(PrivateMsgDTO msg);

    /**
     * 发送客服消息
     *
     * @param msg 消息
     */
    void sendServiceMsg(ServiceMsgDTO msg);
}
