package com.lycodeing.chat.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author xiaotianyu
 * @TableName chat_user
 */
@TableName(value ="chat_user")
@Data
@ToString
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String  nickName;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别 1男 0女
     */
    private Integer gender;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}