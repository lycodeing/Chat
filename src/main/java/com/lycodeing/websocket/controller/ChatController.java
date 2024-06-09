package com.lycodeing.websocket.controller;

import com.lycodeing.websocket.vo.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        chatMessage.setSender(username);
        chatMessage.setSender("server");
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    public void addUser(SimpMessageHeaderAccessor headerAccessor, String token) {
        // 不需要在这里验证token，因为已经在握手拦截器中验证过
    }
}
