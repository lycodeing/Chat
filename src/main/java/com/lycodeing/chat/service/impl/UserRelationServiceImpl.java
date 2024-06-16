package com.lycodeing.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lycodeing.chat.domain.UserRelation;
import com.lycodeing.chat.service.UserRelationService;
import com.lycodeing.chat.mapper.UserRelationMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
* @author xiaotianyu
* @description 针对表【chat_user_relation】的数据库操作Service实现
* @createDate 2024-06-10 14:55:58
*/
@Service
public class UserRelationServiceImpl extends ServiceImpl<UserRelationMapper, UserRelation>
    implements UserRelationService{

    private final UserRelationMapper userRelationMapper;

    public UserRelationServiceImpl(UserRelationMapper userRelationMapper) {
        this.userRelationMapper = userRelationMapper;
    }

    @Override
    public UserRelation getUserRelation(String userId, String toUserId) {
        LambdaQueryWrapper<UserRelation> wrapper = Wrappers.lambdaQuery(UserRelation.class)
                .eq(UserRelation::getUserId, userId)
                .eq(UserRelation::getToUserId, toUserId);
        return userRelationMapper.selectOne(wrapper);
    }

    @Override
    public List<String> getFriendUserIds(String userId) {
        LambdaQueryWrapper<UserRelation> wrapper = Wrappers.lambdaQuery(UserRelation.class)
                .select(UserRelation::getToUserId)
                .eq(UserRelation::getUserId, userId);
        List<UserRelation> userRelations = userRelationMapper.selectList(wrapper);
        return CollectionUtils.isEmpty(userRelations) ? Collections.emptyList() : userRelations.stream().map(UserRelation::getToUserId).toList();
    }
}




