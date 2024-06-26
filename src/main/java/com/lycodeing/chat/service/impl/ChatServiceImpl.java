package com.lycodeing.chat.service.impl;

import static com.lycodeing.chat.dto.GroupMsgDTO.Sender;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lycodeing.chat.config.NettyServiceContext;
import com.lycodeing.chat.domain.GroupUser;
import com.lycodeing.chat.domain.User;
import com.lycodeing.chat.dto.GroupMsgDTO;
import com.lycodeing.chat.dto.PrivateMsgDTO;
import com.lycodeing.chat.dto.ServiceMsgDTO;
import com.lycodeing.chat.service.*;
import com.lycodeing.chat.utils.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author xiaotianyu
 */
@Service
public class ChatServiceImpl implements ChatService {


    private final UserService userService;

    private final GroupUserService groupUserService;

    private final ThreadPoolTaskExecutor executor;

    @Autowired
    public ChatServiceImpl(UserService userService,
                           GroupUserService groupUserService,
                           @Qualifier("customThreadPool") ThreadPoolTaskExecutor executor) {
        this.userService = userService;
        this.groupUserService = groupUserService;
        this.executor = executor;
    }

    @Override
    public void sendGroupMsg(GroupMsgDTO msg) {
        User user = userService.getById(msg.getSenderId());
        List<GroupUser> groupUsers = groupUserService.list(Wrappers.lambdaQuery(GroupUser.class).eq(GroupUser::getGroupId, msg.getGroupId()));
        Map<String, GroupUser> groupUserMap = groupUsers.stream().collect(Collectors.toMap(GroupUser::getUserId, groupUser -> groupUser));
        AssertUtils.isTrue(CollectionUtils.isNotEmpty(groupUsers), "该群组下没有任何用户");

        // 使用线程池发送消息
        groupUsers.stream()
                .map(GroupUser::getUserId)
                .forEach(userId -> executor.execute(() -> {
                    // 获取群组用户信息
                    GroupUser groupUser = groupUserMap.get(userId);
                    // 设置发送人信息
                    msg.setSender(Sender.builder()
                            .senderId(user.getId())
                            .avatar(user.getAvatarUrl())
                            .nickName(StringUtils.isNotBlank(groupUser.getNickName())
                                    ? groupUser.getNickName()
                                    : groupUser.getSourceName())
                            .build());

                    NettyServiceContext.sendMessage(userId, msg.toString());
                }));
    }


    @Override
    public void sendPrivateMsg(PrivateMsgDTO msg) {
        // TODO
    }

    @Override
    public void sendServiceMsg(ServiceMsgDTO msg) {
        // TODO
    }

}
