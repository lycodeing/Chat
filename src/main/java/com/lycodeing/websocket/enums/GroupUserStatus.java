package com.lycodeing.websocket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaotianyu
 */

@AllArgsConstructor
@Getter
public enum GroupUserStatus {
    /**
     * 正常
     */
    NORMAL(1),

    /**
     * 退群
     */
    QUIT(2),

    /**
     * 被踢出群
     */
    KICK(3);
    private final Integer status;


    public static GroupUserStatus getStatus(int status) {
        for (GroupUserStatus value : GroupUserStatus.values()) {
            if (value.getStatus() == status) {
                return value;
            }
        }
        return null;
    }
}
