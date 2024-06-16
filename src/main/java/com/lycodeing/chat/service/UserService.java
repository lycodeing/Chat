package com.lycodeing.chat.service;

import com.lycodeing.chat.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author xiaotianyu
* @description 针对表【chat_user】的数据库操作Service
* @createDate 2024-06-10 14:55:58
*/
public interface UserService extends IService<User> {

    User getUserByUsername(String username);


    /**
     * 获取好友列表
     *
     * @param userId 当前用户ID
     * @return 好友列表
     */
    List<User> getFriendUserList(String userId);

}
