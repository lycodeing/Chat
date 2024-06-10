package com.lycodeing.chat.mapper;

import com.lycodeing.chat.domain.UserRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaotianyu
* @description 针对表【chat_user_relation】的数据库操作Mapper
* @createDate 2024-06-10 14:55:58
* @Entity com.lycodeing.websocket.domain.UserRelation
*/
@Mapper
public interface UserRelationMapper extends BaseMapper<UserRelation> {

}




