package com.lycodeing.chat.controller;

import com.lycodeing.chat.dto.GroupMsgDTO;
import com.lycodeing.chat.dto.PrivateMsgDTO;
import com.lycodeing.chat.service.ChatService;
import com.lycodeing.chat.request.ChatMessageRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaotianyu
 */
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(ChatMessageRequest chatMessage) {
        PrivateMsgDTO privateMsgDTO = PrivateMsgDTO.builder()
                .msg(chatMessage.getMsg())
                .senderId(chatMessage.getSender())
                .receiverId(chatMessage.getReceiver())
                .msgType(chatMessage.getMsgType())
                .build();
        chatService.sendPrivateMsg(privateMsgDTO);
    }


    @MessageMapping("/chat.sendGroupMessage")
    public void sendGroupMessage(ChatMessageRequest chatMessage) {
        GroupMsgDTO groupMsgDTO = GroupMsgDTO.builder()
                .msg(chatMessage.getMsg())
                .senderId(chatMessage.getSender())
                .groupId(chatMessage.getReceiver())
                .msgType(chatMessage.getMsgType())
                .build();
        chatService.sendGroupMsg(groupMsgDTO);
    }
}
