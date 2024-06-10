package com.lycodeing.websocket.service;

import com.lycodeing.websocket.domain.GroupUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author xiaotianyu
* @description 针对表【chat_group_user】的数据库操作Service
* @createDate 2024-06-10 14:55:58
*/
public interface GroupUserService extends IService<GroupUser> {

    /**
     * 检查用户是否在群组内
     * @param userId 用户ID
     * @param groupId 群组ID
     * @return
     */
    boolean isGroupMember(String userId, String groupId);

}
