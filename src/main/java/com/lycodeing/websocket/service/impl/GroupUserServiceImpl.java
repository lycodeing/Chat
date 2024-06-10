package com.lycodeing.websocket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lycodeing.websocket.domain.GroupUser;
import com.lycodeing.websocket.enums.GroupUserStatus;
import com.lycodeing.websocket.service.GroupUserService;
import com.lycodeing.websocket.mapper.GroupUserMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaotianyu
* @description 针对表【chat_group_user】的数据库操作Service实现
* @createDate 2024-06-10 14:55:58
*/
@Service
public class GroupUserServiceImpl extends ServiceImpl<GroupUserMapper, GroupUser>
    implements GroupUserService{

    @Override
    public boolean isGroupMember(String userId, String groupId) {
        if (userId == null || groupId == null) {
            return false;
        }
        LambdaQueryWrapper<GroupUser> wrapper = Wrappers.lambdaQuery(GroupUser.class)
                .eq(GroupUser::getUserId, userId)
                .eq(GroupUser::getStatus, GroupUserStatus.NORMAL.getStatus())
                .eq(GroupUser::getGroupId, groupId);
        return baseMapper.selectCount(wrapper) >= 1;
    }
}




