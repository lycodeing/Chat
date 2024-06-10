package com.lycodeing.websocket.service;

import com.lycodeing.websocket.dto.GroupMsgDTO;
import com.lycodeing.websocket.dto.PrivateMsgDTO;
import com.lycodeing.websocket.dto.ServiceMsgDTO;

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
