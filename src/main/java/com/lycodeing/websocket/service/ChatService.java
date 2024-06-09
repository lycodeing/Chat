package com.lycodeing.websocket.service;

import com.lycodeing.websocket.dto.MsgRequestDTO;

/**
 * @author xiaotianyu
 */
public interface ChatService {

    /**
     * 发送群聊消息
     * @param msg 消息
     */
    void sendGroupMsg(MsgRequestDTO msg);

    /**
     * 发送私聊消息
     * @param msg 消息
     */
    void sendPrivateMsg(MsgRequestDTO msg);

    /**
     * 发送客服消息
     *
     * @param msg 消息
     */
    void sendServiceMsg(MsgRequestDTO msg);
}
