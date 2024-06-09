package com.lycodeing.websocket.service.impl;

import com.lycodeing.websocket.dto.MsgDTO;
import com.lycodeing.websocket.dto.MsgRequestDTO;
import static com.lycodeing.websocket.dto.MsgDTO.Sender;
import static com.lycodeing.websocket.dto.MsgDTO.Receiver;

import com.lycodeing.websocket.enums.MsgTypeEnum;
import com.lycodeing.websocket.enums.SubscribeTypeEnum;
import com.lycodeing.websocket.service.ChatService;
import com.lycodeing.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xiaotianyu
 */
@Service
public class ChatServiceImpl implements ChatService {

    private final SimpMessagingTemplate smtpTemplate;;

    private final UserService userService;


    @Autowired
    public ChatServiceImpl(SimpMessagingTemplate smtpTemplate,
                           UserService userService) {
        this.smtpTemplate = smtpTemplate;
        this.userService = userService;
    }

    @Override
    public void sendGroupMsg(MsgRequestDTO msg) {
        String destination = String.format(SubscribeTypeEnum.GROUP.getUrl(), msg.getReceiver());
        MsgDTO msgDTO = getMsgDTO(msg);
        smtpTemplate.convertAndSend(destination,msgDTO);
    }



    @Override
    public void sendPrivateMsg(MsgRequestDTO msg) {
        String destination = String.format(SubscribeTypeEnum.PRIVATE.getUrl(), msg.getReceiver());
        MsgDTO msgDTO = getMsgDTO(msg);
        smtpTemplate.convertAndSend(destination,msgDTO);
    }

    @Override
    public void sendServiceMsg(MsgRequestDTO msg) {
        String destination = String.format(SubscribeTypeEnum.SERVICE.getUrl(), msg.getReceiver());
        MsgDTO msgDTO = getMsgDTO(msg);
        smtpTemplate.convertAndSend(destination,msgDTO);
    }


    private  MsgDTO getMsgDTO(MsgRequestDTO msg) {
        String sender = userService.getUserById(msg.getSender());
        String receiver = userService.getUserById(msg.getReceiver());
        return MsgDTO.builder()
                .msg(msg.getMsg())
                .msgType(msg.getMsgType())
                .sender(Sender.builder().build())
                .receiver(Receiver.builder().build())
                .sendTime(System.currentTimeMillis())
                .build();
    }
}
