package com.lycodeing.chat.controller;

import com.lycodeing.chat.dto.GroupMsgDTO;
import com.lycodeing.chat.dto.PrivateMsgDTO;
import com.lycodeing.chat.security.AuthenticatedUser;
import com.lycodeing.chat.security.AuthenticatedUserContext;
import com.lycodeing.chat.service.ChatService;
import com.lycodeing.chat.dto.request.ChatMessageRequest;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/sendPrivateMessage")
    public void sendPrivateMessage(ChatMessageRequest chatMessage) {
        // 获取当前登录的用户ID
        AuthenticatedUser authenticatedUser = AuthenticatedUserContext.get();
        PrivateMsgDTO privateMsgDTO = PrivateMsgDTO.builder()
                .msg(chatMessage.getMsg())
                .senderId(authenticatedUser.getUserId())
                .receiverId(chatMessage.getReceiver())
                .msgType(chatMessage.getMsgType())
                .build();
        chatService.sendPrivateMsg(privateMsgDTO);
    }


    @PostMapping("/sendGroupMessage")
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
