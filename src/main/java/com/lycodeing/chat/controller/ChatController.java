package com.lycodeing.chat.controller;

import com.lycodeing.chat.dto.GroupMsgDTO;
import com.lycodeing.chat.dto.PrivateMsgDTO;
import com.lycodeing.chat.security.AuthenticatedUser;
import com.lycodeing.chat.security.AuthenticatedUserContext;
import com.lycodeing.chat.service.ChatService;
import com.lycodeing.chat.dto.request.ChatMessageRequest;
import com.lycodeing.netty.annotations.NettyMessageMapping;
import com.lycodeing.netty.annotations.NettyMessageServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author xiaotianyu
 */
@NettyMessageServer
@Slf4j
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @NettyMessageMapping("/sendPrivateMessage")
    public void sendPrivateMessage(ChatMessageRequest chatMessage) {
        log.info("sendPrivateMessage:{}", chatMessage);
        // 获取当前登录的用户ID
        AuthenticatedUser authenticatedUser = AuthenticatedUserContext.get();

        PrivateMsgDTO privateMsgDTO = PrivateMsgDTO.builder()
                .msg(chatMessage.getMsg())
                .senderId(authenticatedUser.getUserId())
                .receiverId(chatMessage.getReceiver())
                .msgType(chatMessage.getMsgType())
                .sendTime(System.currentTimeMillis())
                .messageId(UUID.randomUUID().toString().replace("-", ""))
                .build();
        chatService.sendPrivateMsg(privateMsgDTO);
    }

    //test1 test2
    @NettyMessageMapping("/sendGroupMessage")
    public void sendGroupMessage(ChatMessageRequest chatMessage) {
        GroupMsgDTO groupMsgDTO = GroupMsgDTO.builder()
                .msg(chatMessage.getMsg())
                .senderId(chatMessage.getSender())
                .groupId(chatMessage.getReceiver())
                .msgType(chatMessage.getMsgType())
                .messageId(UUID.randomUUID().toString().replace("-", ""))
                .build();
        chatService.sendGroupMsg(groupMsgDTO);
    }
}
