package com.lycodeing.websocket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lycodeing.websocket.domain.Group;
import com.lycodeing.websocket.service.GroupService;
import com.lycodeing.websocket.mapper.GroupMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaotianyu
* @description 针对表【chat_group】的数据库操作Service实现
* @createDate 2024-06-10 14:55:58
*/
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group>
    implements GroupService{

}




