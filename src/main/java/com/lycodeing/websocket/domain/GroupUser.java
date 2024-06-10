package com.lycodeing.websocket.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author xiaotianyu
 * @TableName chat_group_user
 */
@TableName(value ="chat_group_user")
@Data
@ToString
public class GroupUser implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 群组id
     */
    private String groupId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 原始昵称
     */
    private String sourceName;

    /**
     * 群昵称
     */
    private String nickName;

    /**
     * 角色 0群主 1管理员 2普通成员
     */
    private Integer role;

    /**
     * 加入群组时间
     */
    private Date joinTime;

    /**
     * 状态：1：正常；2：退群；3：被踢出群
     */
    private Integer status;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}