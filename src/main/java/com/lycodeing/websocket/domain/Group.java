package com.lycodeing.websocket.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @TableName chat_group
 */
@TableName(value ="chat_group")
@Data
@ToString
public class Group implements Serializable {
    /**
     * 群组主键
     */
    @TableId
    private String id;

    /**
     * 群组名称
     */
    private String groupName;

    /**
     * 人数上限
     */
    private Integer num;

    /**
     * 群组头像
     */
    private String avatarUrl;

    /**
     * 群组简介
     */
    private String description;

    /**
     * 状态 1正常 2封群 3删群
     */
    private Integer status;

    /**
     * 进群方式  1任意 2需审核
     */
    private Integer joinMethod;

    /**
     * 禁言状态  0没禁言 1禁言
     */
    private Integer isMuted;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}