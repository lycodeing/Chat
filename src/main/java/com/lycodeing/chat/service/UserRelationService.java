package com.lycodeing.chat.service;

import com.lycodeing.chat.domain.UserRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author xiaotianyu
* @description 针对表【chat_user_relation】的数据库操作Service
* @createDate 2024-06-10 14:55:58
*/
public interface UserRelationService extends IService<UserRelation> {

    /**
     * 查询好友关系
     * @param userId 当前用户id
     * @param toUserId 好友id
     * @return UserRelation
     */
    UserRelation getUserRelation(String userId,String toUserId);

    /**
     * 获取好友id列表
     *
     * @param userId 当前用户id
     * @return 好友列表
     */
    List<String> getFriendUserIds(String userId);
}
