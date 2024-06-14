package com.lycodeing.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lycodeing.chat.domain.User;
import com.lycodeing.chat.service.UserRelationService;
import com.lycodeing.chat.service.UserService;
import com.lycodeing.chat.mapper.UserMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author xiaotianyu
* @description 针对表【chat_user】的数据库操作Service实现
* @createDate 2024-06-10 14:55:58
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserRelationService userRelationService;

    public UserServiceImpl(UserRelationService userRelationService) {
        this.userRelationService = userRelationService;
    }


    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery(User.class);
        lambdaQuery.eq(User::getUserName, username);
        return baseMapper.selectOne(lambdaQuery);
    }

    @Override
    public List<User> getFriendUserList(String userId) {
        List<String> friendUserIds = userRelationService.getFriendUserIds(userId);
        if (CollectionUtils.isEmpty(friendUserIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery(User.class);
        lambdaQuery.in(User::getId, friendUserIds);
        return this.baseMapper.selectList(lambdaQuery);
    }
}




