package com.lycodeing.websocket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lycodeing.websocket.domain.GroupUser;
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

}




