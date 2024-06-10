package com.lycodeing.websocket.controller;

import com.lycodeing.websocket.vo.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {


    @MessageMapping("/chat.sendPrivateMessage")
    @SendTo("/topic/public")
    public ChatMessage sendPrivateMessage(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        chatMessage.setSender(username);
        chatMessage.setSender("server");
        return chatMessage;
    }


    @MessageMapping("/chat.sendGroupMessage")
    @SendTo("/topic/public")
    public ChatMessage sendGroupMessage(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        chatMessage.setSender(username);
        chatMessage.setSender("server");
        return chatMessage;
    }
}
