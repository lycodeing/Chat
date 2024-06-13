package com.lycodeing.chat.service.impl;

import static com.lycodeing.chat.dto.GroupMsgDTO.Sender;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lycodeing.chat.domain.GroupUser;
import com.lycodeing.chat.domain.User;
import com.lycodeing.chat.dto.GroupMsgDTO;
import com.lycodeing.chat.dto.PrivateMsgDTO;
import com.lycodeing.chat.dto.ServiceMsgDTO;
import com.lycodeing.chat.enums.SubscribeTypeEnum;
import com.lycodeing.chat.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xiaotianyu
 */
@Service
public class ChatServiceImpl implements ChatService {

    private final SimpMessagingTemplate smtpTemplate;
    ;

    private final UserService userService;


    private final GroupUserService groupUserService;

    @Autowired
    public ChatServiceImpl(SimpMessagingTemplate smtpTemplate,
                           UserService userService, GroupUserService groupUserService) {
        this.smtpTemplate = smtpTemplate;
        this.userService = userService;
        this.groupUserService = groupUserService;
    }

    @Override
    public void sendGroupMsg(GroupMsgDTO msg) {
        String destination = String.format(SubscribeTypeEnum.GROUP.getUrl(), msg.getGroupId());
        User user = userService.getById(msg.getSenderId());
        GroupUser groupUser = groupUserService.getOne(Wrappers.lambdaQuery(GroupUser.class)
                .eq(GroupUser::getGroupId, msg.getGroupId())
                .eq(GroupUser::getUserId, user.getId())
        );
        // 设置发送人信息
        msg.setSender(Sender.builder()
                .senderId(user.getId())
                .avatar(user.getAvatarUrl())
                .nickName(StringUtils.isNotBlank(groupUser.getNickName()) ? groupUser.getNickName() : groupUser.getSourceName())
                .build());
        smtpTemplate.convertAndSend(destination, msg);
    }


    @Override
    public void sendPrivateMsg(PrivateMsgDTO msg) {
        String destination = String.format(SubscribeTypeEnum.PRIVATE.getUrl(), msg.getReceiverId());
        smtpTemplate.convertAndSend(destination, msg);
    }

    @Override
    public void sendServiceMsg(ServiceMsgDTO msg) {
        String destination = String.format(SubscribeTypeEnum.SERVICE.getUrl(), msg.getServiceId());
        smtpTemplate.convertAndSend(destination, msg);
    }

}
