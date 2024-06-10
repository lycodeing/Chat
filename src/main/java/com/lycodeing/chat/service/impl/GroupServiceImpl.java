package com.lycodeing.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lycodeing.chat.domain.Group;
import com.lycodeing.chat.service.GroupService;
import com.lycodeing.chat.mapper.GroupMapper;
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




